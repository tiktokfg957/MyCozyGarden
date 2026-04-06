package com.example.mycozygarden.ui.upgrades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycozygarden.data.model.Upgrade
import com.example.mycozygarden.databinding.ItemUpgradeBinding

class UpgradeAdapter(
    private val onBuyClick: (Upgrade) -> Unit
) : RecyclerView.Adapter<UpgradeAdapter.ViewHolder>() {

    private var items = listOf<Upgrade>()

    fun submitList(list: List<Upgrade>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUpgradeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemUpgradeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(upgrade: Upgrade) {
            binding.tvName.text = upgrade.name
            binding.tvDescription.text = upgrade.description
            binding.tvPrice.text = "Цена: ${upgrade.basePrice} монет"
            binding.btnBuy.isEnabled = !upgrade.owned
            binding.btnBuy.setOnClickListener {
                onBuyClick(upgrade)
            }
        }
    }
}
