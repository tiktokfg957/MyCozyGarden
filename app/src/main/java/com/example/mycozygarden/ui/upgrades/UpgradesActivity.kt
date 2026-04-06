package com.example.mycozygarden.ui.upgrades

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycozygarden.databinding.ActivityUpgradesBinding

class UpgradesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpgradesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpgradesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Toast.makeText(this, "Улучшения в разработке", Toast.LENGTH_SHORT).show()
        finish()
    }
}
