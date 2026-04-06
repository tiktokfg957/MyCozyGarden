package com.example.mycozygarden.ui.game

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mycozygarden.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var adapter: GardenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Мой уютный огород"

        // 12 грядок
        val beds = List(12) { index ->
            GardenBedData(
                index = index,
                cropName = if (index % 3 == 0) "Картофель" else null,
                progress = if (index % 3 == 0) 0.75f else 0f
            )
        }

        adapter = GardenAdapter(beds) { bed, action ->
            when (action) {
                GardenAction.CLICK -> {
                    Toast.makeText(this, "Грядка ${bed.index + 1} ускорена!", Toast.LENGTH_SHORT).show()
                }
                GardenAction.HARVEST -> {
                    Toast.makeText(this, "Собрано с грядки ${bed.index + 1}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.rvGarden.layoutManager = GridLayoutManager(this, 4)
        binding.rvGarden.adapter = adapter

        binding.btnBackToMenu.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

data class GardenBedData(
    val index: Int,
    val cropName: String?,
    val progress: Float
)

enum class GardenAction { CLICK, HARVEST }
