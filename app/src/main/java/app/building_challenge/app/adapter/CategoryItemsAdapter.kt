package app.building_challenge.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.building_challenge.data.models.Item
import app.building_challenge.databinding.ItemListCategoryBinding

class CategoryItemsAdapter : ListAdapter<Item,
        CategoryListViewHolder>(CategoryListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListCategoryBinding.inflate(inflater, parent, false)
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}

class CategoryListViewHolder(private val binding: ItemListCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(category: Item) {
        binding.categoryItems = category
        binding.executePendingBindings()
    }
}

class CategoryListDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}