package com.example.mycozygarden.ui.achievements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycozygarden.data.model.Achievement
import com.example.mycozygarden.databinding.ItemAchievementBinding

class AchievementAdapter : RecyclerView.Adapter<AchievementAdapter.ViewHolder>() {

    private var items = listOf<Achievement>()

    fun submitList(list: List<Achievement>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemAchievementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(achievement: Achievement) {
            binding.tvTitle.text = achievement.name
            binding.tvDescription.text = achievement.description
            val progressPercent = if (achievement.requirementValue > 0) (achievement.progress.toFloat() / achievement.requirementValue * 100).toInt() else 0
            binding.progressBar.progress = progressPercent
            binding.tvProgress.text = "${achievement.progress}/${achievement.requirementValue}"
            val rewardText = when {
                achievement.rewardCoins > 0 && achievement.rewardGems > 0 -> "Награда: ${achievement.rewardCoins} монет, ${achievement.rewardGems} алмазов"
                achievement.rewardCoins > 0 -> "Награда: ${achievement.rewardCoins} монет"
                achievement.rewardGems > 0 -> "Награда: ${achievement.rewardGems} алмазов"
                else -> ""
            }
            binding.tvReward.text = rewardText
            if (achievement.completed) {
                binding.progressBar.progressTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FFD700"))
                binding.tvTitle.setBackgroundColor(android.graphics.Color.parseColor("#4CAF50"))
            }
        }
    }
}
