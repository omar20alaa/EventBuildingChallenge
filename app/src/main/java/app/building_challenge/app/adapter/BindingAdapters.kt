package app.building_challenge.app.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.building_challenge.R
import app.building_challenge.data.models.Category
import app.building_challenge.data.models.Item
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.StateFlow

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("categoryList")
    fun bindCategoryList(recyclerView: RecyclerView, categories: StateFlow<List<Category>>?) {
        categories?.let { stateFlow ->
            recyclerView.visibility = if (stateFlow.value.isEmpty()) View.GONE else View.VISIBLE
            val adapter = recyclerView.adapter as? CategoryAdapter
            adapter?.submitList(stateFlow.value)
        }
    }

    @JvmStatic
    @BindingAdapter("categoryItemsList")
    fun bindCategoryItemsList(recyclerView: RecyclerView, categories: StateFlow<List<Item>>?) {
        categories?.let { stateFlow ->
            recyclerView.visibility = if (stateFlow.value.isEmpty()) View.GONE else View.VISIBLE
            val adapter = recyclerView.adapter as? CategoryItemsAdapter
            adapter?.submitList(stateFlow.value)
        }
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, imageUrl: String?) {
        if (imageUrl.isNullOrEmpty()) {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    imageView.context,
                    R.drawable.place_holder
                )
            )
        } else {
            Glide.with(imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(imageView)
        }
    }
}