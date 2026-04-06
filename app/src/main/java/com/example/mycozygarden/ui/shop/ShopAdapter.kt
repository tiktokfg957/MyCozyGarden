package com.example.mycozygarden.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycozygarden.data.model.Upgrade
import com.example.mycozygarden.databinding.ItemShopBinding

class ShopAdapter(
    private val upgrades: List<Upgrade>,
    private val onBuyClick: (Upgrade) -> Unit
) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(upgrades[position])
    }

    override fun getItemCount() = upgrades.size

    inner class ViewHolder(private val binding: ItemShopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(upgrade: Upgrade) {
            binding.tvName.text = upgrade.name
            binding.tvDescription.text = upgrade.description
            binding.tvPrice.text = "Цена: ${upgrade.basePrice} монет"
            binding.btnBuy.isEnabled = !upgrade.owned
            binding.btnBuy.text = if (upgrade.owned) "Куплено" else "Купить"
            binding.btnBuy.setOnClickListener {
                onBuyClick(upgrade)
            }
        }
    }
}
