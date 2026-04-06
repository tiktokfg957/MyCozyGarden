package com.example.mycozygarden.ui.game

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mycozygarden.MyApplication
import com.example.mycozygarden.databinding.ActivityGameBinding
import com.example.mycozygarden.ui.achievements.AchievementsActivity
import com.example.mycozygarden.ui.settings.SettingsActivity
import com.example.mycozygarden.ui.shop.ShopActivity
import com.example.mycozygarden.ui.upgrades.UpgradesActivity

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private val repository by lazy { (application as MyApplication).repository }
    private val viewModel: GameViewModel by viewModels {
        GameViewModel.GameViewModelFactory(repository)
    }
    private lateinit var adapter: GardenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            binding = ActivityGameBinding.inflate(layoutInflater)
            setContentView(binding.root)

            setupRecyclerView()
            observeData()
            setupListeners()
        } catch (e: Exception) {
            Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = GardenAdapter(
            onHarvestClick = { bed -> viewModel.onHarvest(bed) },
            onBedClick = { bed -> viewModel.onBedClick(bed) }
        )
        binding.rvGarden.layoutManager = GridLayoutManager(this, 4)
        binding.rvGarden.adapter = adapter
    }

    private fun observeData() {
        viewModel.beds.observe(this) { beds ->
            adapter.submitList(beds)
        }
        viewModel.gameState.observe(this) { state ->
            binding.tvCoins.text = state.coins.toString()
            binding.tvGems.text = state.gems.toString()
        }
    }

    private fun setupListeners() {
        binding.btnPlant.setOnClickListener {
            Toast.makeText(this, "Посадка в разработке", Toast.LENGTH_SHORT).show()
        }
        binding.btnUpgrades.setOnClickListener {
            startActivity(Intent(this, UpgradesActivity::class.java))
        }
        binding.btnShop.setOnClickListener {
            startActivity(Intent(this, ShopActivity::class.java))
        }
        binding.btnAchievements.setOnClickListener {
            startActivity(Intent(this, AchievementsActivity::class.java))
        }
        binding.ivSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.ivStats.setOnClickListener {
            Toast.makeText(this, "Статистика в разработке", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.beds.observe(this) { beds ->
            adapter.submitList(beds)
        }
    }
}
