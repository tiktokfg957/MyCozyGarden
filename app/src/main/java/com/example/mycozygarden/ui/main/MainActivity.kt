package com.example.mycozygarden.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycozygarden.databinding.ActivityMainBinding
import com.example.mycozygarden.ui.game.GameActivity

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
            Toast.makeText(this, "Настройки будут позже", Toast.LENGTH_SHORT).show()
        }
        binding.btnShop.setOnClickListener {
            Toast.makeText(this, "Магазин будет позже", Toast.LENGTH_SHORT).show()
        }
        binding.btnExit.setOnClickListener {
            finishAffinity()
        }
    }
}
