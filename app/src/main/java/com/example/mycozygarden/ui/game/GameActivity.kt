package com.example.mycozygarden.ui.game

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(android.R.layout.simple_list_item_1) // временно простой layout
        Toast.makeText(this, "Игровой экран открылся", Toast.LENGTH_LONG).show()
    }
}
