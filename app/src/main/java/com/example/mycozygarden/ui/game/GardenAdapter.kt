package com.example.mycozygarden.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycozygarden.data.model.GardenBed
import com.example.mycozygarden.databinding.ItemGardenBedBinding

class GardenAdapter(
    private val onHarvestClick: (GardenBed) -> Unit,
    private val onBedClick: (GardenBed) -> Unit
) : RecyclerView.Adapter<GardenAdapter.ViewHolder>() {

    private var beds = listOf<GardenBed>()

    fun submitList(list: List<GardenBed>) {
        beds = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGardenBedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(beds[position])
    }

    override fun getItemCount() = beds.size

    inner class ViewHolder(private val binding: ItemGardenBedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bed: GardenBed) {
            val progressPercent = (bed.progress * 100).toInt()
            binding.progressBar.progress = progressPercent
            binding.tvCropName.text = bed.cropType ?: "Пусто"
            binding.btnHarvest.visibility = if (bed.progress >= 1f && bed.cropType != null) android.view.View.VISIBLE else android.view.View.GONE

            binding.btnHarvest.setOnClickListener {
                onHarvestClick(bed)
            }
            binding.root.setOnClickListener {
                onBedClick(bed)
            }
        }
    }
}
