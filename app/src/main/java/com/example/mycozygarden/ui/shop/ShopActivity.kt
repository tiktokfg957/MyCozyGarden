package com.example.mycozygarden.ui.shop

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycozygarden.databinding.ActivityShopBinding

class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var adapter: UpgradeAdapter
    private var coins = 0

    private val upgrades = listOf(
        UpgradeItem("scarecrow", "Пугало", "Увеличивает доход от урожая на 20%", 500, false),
        UpgradeItem("tractor", "Трактор", "Увеличивает силу клика на 0.03", 800, false),
        UpgradeItem("auto_water", "Поливалка", "Автоматически поливает грядку раз в 10 секунд", 600, false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Магазин улучшений"

        prefs = getSharedPreferences("game_data", MODE_PRIVATE)

        loadCoins()
        loadUpgradesStatus()

        adapter = UpgradeAdapter(upgrades) { upgrade ->
            buyUpgrade(upgrade)
        }
        binding.rvUpgrades.layoutManager = LinearLayoutManager(this)
        binding.rvUpgrades.adapter = adapter

        binding.tvCoins.text = "Монеты: $coins"
    }

    private fun loadCoins() {
        coins = prefs.getInt("coins", 100)
    }

    private fun loadUpgradesStatus() {
        upgrades.forEach {
            it.owned = prefs.getBoolean("upgrade_${it.id}", false)
        }
    }

    private fun buyUpgrade(upgrade: UpgradeItem) {
        if (upgrade.owned) {
            Toast.makeText(this, "Улучшение уже куплено", Toast.LENGTH_SHORT).show()
            return
        }
        if (coins >= upgrade.price) {
            coins -= upgrade.price
            upgrade.owned = true
            // Сохраняем
            prefs.edit().putInt("coins", coins).apply()
            prefs.edit().putBoolean("upgrade_${upgrade.id}", true).apply()
            // Обновляем UI
            binding.tvCoins.text = "Монеты: $coins"
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "${upgrade.name} куплено!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Недостаточно монет", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
