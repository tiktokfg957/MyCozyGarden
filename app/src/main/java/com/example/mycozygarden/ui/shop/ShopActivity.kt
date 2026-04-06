package com.example.mycozygarden.ui.shop

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycozygarden.databinding.ActivityShopBinding

class ShopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Toast.makeText(this, "Магазин в разработке", Toast.LENGTH_SHORT).show()
        finish()
    }
}
