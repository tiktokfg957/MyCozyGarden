package com.example.mycozygarden.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycozygarden.databinding.ItemUpgradeBinding

data class UpgradeItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    var owned: Boolean
)

class UpgradeAdapter(
    private val items: List<UpgradeItem>,
    private val onBuyClick: (UpgradeItem) -> Unit
) : RecyclerView.Adapter<UpgradeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUpgradeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemUpgradeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UpgradeItem) {
            binding.tvName.text = item.name
            binding.tvDescription.text = item.description
            binding.tvPrice.text = "Цена: ${item.price} монет"
            binding.btnBuy.isEnabled = !item.owned
            binding.btnBuy.setOnClickListener {
                onBuyClick(item)
            }
        }
    }
}
