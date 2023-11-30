package app.building_challenge.app.presentation.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.building_challenge.R
import app.building_challenge.app.adapter.CategoryItemsAdapter
import app.building_challenge.app.presentation.viewmodels.EventBuildingViewModel
import app.building_challenge.databinding.ActivityCategoryItemsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryItemsBinding
    private val viewModel: EventBuildingViewModel by viewModels()
    private lateinit var connectivityManager: ConnectivityManager
    private var categoryId: Int = -1
    private lateinit var categoryItemsAdapter: CategoryItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupViews()
    }

    private fun setupEvents() {
        binding.closeButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvCategoryItems.layoutManager = layoutManager
        binding.rvCategoryItems.itemAnimator = null;
        categoryItemsAdapter = CategoryItemsAdapter()
        binding.rvCategoryItems.adapter = categoryItemsAdapter
    }

    private fun setupViews() {
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        receiveData()
        observeViewModel()
        setupEvents()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.isNetworkError.observe(this) { isNetworkError ->
            if (isNetworkError) {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.fetchItemsForCategory(connectivityManager, categoryId)
        lifecycleScope.launch {
            viewModel.itemsForCategory.collect { categories ->
                categoryItemsAdapter.submitList(categories)
            }
        }
    }

    private fun receiveData() {
        val title = intent.getStringExtra("title") ?: getString(R.string.app_name)
        binding.textViewTitle.text = title
        categoryId = intent.getIntExtra("categoryId", -1)
    }

}