package com.example.mycozygarden.ui.game

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mycozygarden.R
import com.example.mycozygarden.data.model.Crop
import com.example.mycozygarden.data.model.CropType
import com.example.mycozygarden.databinding.ActivityGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class GardenBedData(
    val index: Int,
    var cropType: String?,
    var progress: Float
)

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var adapter: GardenAdapter
    private lateinit var prefs: SharedPreferences

    private val beds = mutableListOf<GardenBedData>()
    private var coins = 0
        set(value) {
            field = value
            binding.tvCoins.text = "$value"
            saveCoins()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Мой уютный огород"

        prefs = getSharedPreferences("game_data", MODE_PRIVATE)

        loadCoins()
        loadBeds()

        setupRecyclerView()
        startPassiveGrowth()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = GardenAdapter(
            beds = beds,
            onHarvestClick = { bedIndex -> harvestBed(bedIndex) },
            onBedClick = { bedIndex ->
                val bed = beds[bedIndex]
                if (bed.cropType == null) {
                    showPlantDialog(bedIndex)
                } else {
                    val crop = Crop.getByType(CropType.valueOf(bed.cropType!!))
                    val increment = 0.05f
                    val newProgress = (bed.progress + increment).coerceAtMost(1f)
                    bed.progress = newProgress
                    adapter.notifyItemChanged(bedIndex)
                    saveBeds()
                    Toast.makeText(this, "${crop.name} ускорен!", Toast.LENGTH_SHORT).show()
                }
            }
        )
        binding.rvGarden.layoutManager = GridLayoutManager(this, 4)
        binding.rvGarden.adapter = adapter
    }

    private fun showPlantDialog(bedIndex: Int) {
        val sortedCrops = Crop.all.sortedBy { it.priceToPlant }
        val cropNames = sortedCrops.map { "${it.name} (${it.priceToPlant} монет)" }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("Выберите культуру")
            .setItems(cropNames) { _, which ->
                val selectedCrop = sortedCrops[which]
                if (coins >= selectedCrop.priceToPlant) {
                    coins -= selectedCrop.priceToPlant
                    beds[bedIndex].cropType = selectedCrop.type.name
                    beds[bedIndex].progress = 0f
                    adapter.notifyItemChanged(bedIndex)
                    saveBeds()
                    Toast.makeText(this, "${selectedCrop.name} посажен!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Недостаточно монет!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun harvestBed(bedIndex: Int) {
        val bed = beds[bedIndex]
        val cropType = bed.cropType
        if (cropType != null && bed.progress >= 1f) {
            val crop = Crop.getByType(CropType.valueOf(cropType))
            coins += crop.baseHarvestValue
            beds[bedIndex].cropType = null
            beds[bedIndex].progress = 0f
            adapter.notifyItemChanged(bedIndex)
            saveBeds()
            Toast.makeText(this, "Собрано ${crop.name}! +${crop.baseHarvestValue} монет", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startPassiveGrowth() {
        lifecycleScope.launch {
            while (true) {
                delay(1000L)
                var changed = false
                for (i in beds.indices) {
                    val bed = beds[i]
                    if (bed.cropType != null && bed.progress < 1f) {
                        val crop = Crop.getByType(CropType.valueOf(bed.cropType!!))
                        val growthPerSec = 1f / crop.baseGrowthTimeSec
                        val newProgress = (bed.progress + growthPerSec).coerceAtMost(1f)
                        if (newProgress != bed.progress) {
                            bed.progress = newProgress
                            changed = true
                        }
                    }
                }
                if (changed) {
                    adapter.notifyDataSetChanged()
                    saveBeds()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnBackToMenu.setOnClickListener {
            finish()
        }
    }

    private fun saveCoins() {
        prefs.edit().putInt("coins", coins).apply()
    }

    private fun loadCoins() {
        coins = prefs.getInt("coins", 100)
    }

    private fun saveBeds() {
        val json = beds.joinToString(separator = ";") { "${it.cropType}|${it.progress}" }
        prefs.edit().putString("beds", json).apply()
    }

    private fun loadBeds() {
        val json = prefs.getString("beds", "")
        if (json.isNullOrEmpty()) {
            for (i in 0 until 12) {
                beds.add(GardenBedData(i, null, 0f))
            }
        } else {
            val parts = json.split(";")
            for (i in 0 until 12) {
                val part = if (i < parts.size) parts[i] else "null|0.0"
                val (cropType, progressStr) = part.split("|")
                val crop = if (cropType == "null") null else cropType
                val progress = progressStr.toFloatOrNull() ?: 0f
                beds.add(GardenBedData(i, crop, progress))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
