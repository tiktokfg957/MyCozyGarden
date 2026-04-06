package com.example.mycozygarden.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycozygarden.databinding.ItemGardenBedBinding

class GardenAdapter(
    private val beds: List<GardenBedData>,
    private val onHarvestClick: (Int) -> Unit,
    private val onBedClick: (Int) -> Unit
) : RecyclerView.Adapter<GardenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGardenBedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(beds[position], position)
    }

    override fun getItemCount() = beds.size

    inner class ViewHolder(private val binding: ItemGardenBedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bed: GardenBedData, position: Int) {
            val cropName = if (bed.cropType != null) {
                CropType.valueOf(bed.cropType!!).let { Crop.getByType(it).name }
            } else "Пусто"
            binding.tvCropName.text = cropName
            val progressPercent = (bed.progress * 100).toInt()
            binding.progressBar.progress = progressPercent
            val isReady = bed.cropType != null && bed.progress >= 1f
            binding.btnHarvest.visibility = if (isReady) android.view.View.VISIBLE else android.view.View.GONE

            binding.root.setOnClickListener {
                onBedClick(position)
            }
            binding.btnHarvest.setOnClickListener {
                onHarvestClick(position)
            }
        }
    }
}
