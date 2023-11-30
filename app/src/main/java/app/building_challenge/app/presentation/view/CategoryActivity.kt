package app.building_challenge.app.presentation.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.building_challenge.R
import app.building_challenge.app.adapter.CategoryAdapter
import app.building_challenge.app.adapter.CategoryClickListener
import app.building_challenge.app.presentation.viewmodels.EventBuildingViewModel
import app.building_challenge.databinding.ActivityCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() , CategoryClickListener {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: EventBuildingViewModel by viewModels()
    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        displayData()
    }

    private fun displayData() {
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvCategories.layoutManager = layoutManager
        binding.rvCategories.itemAnimator = null;
        categoryAdapter = CategoryAdapter(this)
        binding.rvCategories.adapter = categoryAdapter
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.fetchCategories(connectivityManager)
        lifecycleScope.launch {
            viewModel.categories.collect { categories ->
                categoryAdapter.submitList(categories)
            }
        }
        viewModel.isNetworkError.observe(this) { isNetworkError ->
            if (isNetworkError) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onCategoryItemClick(categoryId: Int, title: String) {
        val intent = Intent(this, CategoryItemsActivity::class.java).apply {
            putExtra("categoryId", categoryId)
            putExtra("title", title)
        }
        startActivity(intent)
    }
}