package com.example.mycozygarden.ui.shop

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycozygarden.data.model.Upgrade
import com.example.mycozygarden.databinding.ActivityShopBinding

class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var adapter: ShopAdapter
    private var coins = 0
    private val upgrades = mutableListOf(
        Upgrade("auto_water", "Поливалка", "Автоматически поливает грядки", 500),
        Upgrade("scarecrow", "Пугало", "+20% к стоимости урожая", 1000),
        Upgrade("tractor", "Трактор", "Увеличивает силу клика", 1500)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Магазин улучшений"

        prefs = getSharedPreferences("game_data", MODE_PRIVATE)
        coins = prefs.getInt("coins", 100)

        loadUpgradesStatus()
        setupRecyclerView()

        binding.tvCoins.text = "$coins"
    }

    private fun loadUpgradesStatus() {
        for (upgrade in upgrades) {
            upgrade.owned = prefs.getBoolean("upgrade_${upgrade.id}", false)
        }
    }

    private fun setupRecyclerView() {
        adapter = ShopAdapter(upgrades) { upgrade ->
            if (!upgrade.owned && coins >= upgrade.basePrice) {
                coins -= upgrade.basePrice
                upgrade.owned = true
                prefs.edit()
                    .putInt("coins", coins)
                    .putBoolean("upgrade_${upgrade.id}", true)
                    .apply()
                binding.tvCoins.text = "$coins"
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "${upgrade.name} куплено!", Toast.LENGTH_SHORT).show()
            } else if (upgrade.owned) {
                Toast.makeText(this, "Уже куплено", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Недостаточно монет", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvUpgrades.layoutManager = LinearLayoutManager(this)
        binding.rvUpgrades.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
