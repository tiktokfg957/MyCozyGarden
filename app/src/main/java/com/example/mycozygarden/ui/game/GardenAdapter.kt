package com.example.mycozygarden.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycozygarden.R
import com.example.mycozygarden.data.model.Crop
import com.example.mycozygarden.data.model.CropType
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
            // Получаем ресурс картинки для растения или показываем заглушку
            val cropResId = if (bed.cropType != null) {
                getImageForCrop(bed.cropType)
            } else {
                R.drawable.empty_plot // или 0, тогда скроем ImageView
            }
            if (cropResId != 0) {
                binding.ivPlant.setImageResource(cropResId)
                binding.ivPlant.visibility = android.view.View.VISIBLE
            } else {
                binding.ivPlant.visibility = android.view.View.GONE
            }

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

        private fun getImageForCrop(cropTypeStr: String): Int {
            return when (cropTypeStr) {
                CropType.POTATO.name -> R.drawable.potato
                CropType.TOMATO.name -> R.drawable.tomato
                CropType.SUNFLOWER.name -> R.drawable.sunflower
                CropType.STRAWBERRY.name -> R.drawable.strawberry
                CropType.PUMPKIN.name -> R.drawable.pumpkin
                CropType.CORN.name -> R.drawable.corn
                CropType.PEPPER.name -> R.drawable.pepper
                // Новые культуры, если добавите
                "CARROT" -> R.drawable.carrot
                "WHEAT" -> R.drawable.wheat
                "RADISH" -> R.drawable.radish
                else -> 0
            }
        }
    }
}
