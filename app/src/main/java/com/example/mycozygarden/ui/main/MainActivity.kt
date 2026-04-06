package com.example.mycozygarden.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycozygarden.databinding.ActivityMainBinding
import com.example.mycozygarden.ui.game.GameActivity
import com.example.mycozygarden.ui.settings.SettingsActivity
import com.example.mycozygarden.ui.shop.ShopActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.btnShop.setOnClickListener {
            startActivity(Intent(this, ShopActivity::class.java))
        }
        binding.btnExit.setOnClickListener {
            finishAffinity()
        }
    }
}
