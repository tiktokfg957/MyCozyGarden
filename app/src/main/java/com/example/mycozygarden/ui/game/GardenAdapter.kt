package com.example.mycozygarden.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycozygarden.databinding.ItemGardenBedBinding

class GardenAdapter(
    private val beds: List<GardenBedData>,
    private val onAction: (GardenBedData, GardenAction) -> Unit
) : RecyclerView.Adapter<GardenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGardenBedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(beds[position])
    }

    override fun getItemCount() = beds.size

    inner class ViewHolder(private val binding: ItemGardenBedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bed: GardenBedData) {
            binding.tvCropName.text = bed.cropName ?: "Пусто"
            val progressPercent = (bed.progress * 100).toInt()
            binding.progressBar.progress = progressPercent
            val isReady = bed.cropName != null && bed.progress >= 1f
            binding.btnHarvest.visibility = if (isReady) android.view.View.VISIBLE else android.view.View.GONE

            binding.root.setOnClickListener {
                if (bed.cropName != null && !isReady) {
                    onAction(bed, GardenAction.CLICK)
                }
            }
            binding.btnHarvest.setOnClickListener {
                if (isReady) {
                    onAction(bed, GardenAction.HARVEST)
                }
            }
        }
    }
}
