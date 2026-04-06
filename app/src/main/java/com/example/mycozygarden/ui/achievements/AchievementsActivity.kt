package com.example.mycozygarden.ui.achievements

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycozygarden.MyApplication
import com.example.mycozygarden.databinding.ActivityAchievementsBinding
import com.example.mycozygarden.ui.achievements.AchievementsViewModel
import kotlinx.coroutines.launch

class AchievementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAchievementsBinding
    private val repository by lazy { (application as MyApplication).repository }
    private val viewModel by viewModels<AchievementsViewModel> {
        AchievementsViewModel.AchievementsViewModelFactory(repository)
    }
    private lateinit var adapter: AchievementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Достижения"

        adapter = AchievementAdapter()
        binding.rvAchievements.layoutManager = LinearLayoutManager(this)
        binding.rvAchievements.adapter = adapter

        viewModel.achievements.observe(this) { achievements ->
            adapter.submitList(achievements)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
