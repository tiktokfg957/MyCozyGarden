package com.example.mycozygarden.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mycozygarden.data.database.AppDatabase
import com.example.mycozygarden.databinding.ActivitySettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var prefs: SharedPreferences
    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("game_settings", MODE_PRIVATE)

        binding.sbMusic.progress = prefs.getInt("music_volume", 50)
        binding.sbSound.progress = prefs.getInt("sound_volume", 50)
        binding.cbVibration.isChecked = prefs.getBoolean("vibration", true)

        binding.sbMusic.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                prefs.edit().putInt("music_volume", progress).apply()
            }
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })
        binding.sbSound.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                prefs.edit().putInt("sound_volume", progress).apply()
            }
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })
        binding.cbVibration.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("vibration", isChecked).apply()
        }
        binding.btnResetProgress.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Сброс прогресса")
                .setMessage("Вы уверены? Весь прогресс будет удалён безвозвратно.")
                .setPositiveButton("Да") { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        db.clearAllTables()
                        runOnUiThread {
                            Toast.makeText(this@SettingsActivity, "Прогресс сброшен", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
                .setNegativeButton("Нет", null)
                .show()
        }
    }
}
