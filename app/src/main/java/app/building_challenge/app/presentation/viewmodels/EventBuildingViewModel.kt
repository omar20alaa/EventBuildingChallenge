package app.building_challenge.app.presentation.viewmodels

import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.building_challenge.app.utils.NetworkUtils
import app.building_challenge.data.models.Category
import app.building_challenge.data.models.Item
import app.building_challenge.domain.usecases.FetchCategoriesAndItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventBuildingViewModel @Inject constructor(
    private val fetchCategoriesAndItemsForCategoryUseCase: FetchCategoriesAndItemsUseCase,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _itemsForCategory = MutableStateFlow<List<Item>>(emptyList())
    val itemsForCategory: StateFlow<List<Item>> = _itemsForCategory.asStateFlow()

    private val _isNetworkError = MutableLiveData<Boolean>()
    val isNetworkError: LiveData<Boolean> get() = _isNetworkError

    fun fetchCategories(connectivityManager: ConnectivityManager) {
        if (NetworkUtils.isInternetAvailable(connectivityManager)) {
            _isLoading.postValue(true)
            viewModelScope.launch {
                _categories.value = fetchCategoriesAndItemsForCategoryUseCase()
                _isLoading.postValue(false)
            }
        } else {
            _isNetworkError.postValue(true)
        }
    }

    fun fetchItemsForCategory(connectivityManager: ConnectivityManager, categoryId: Int) {
        if (NetworkUtils.isInternetAvailable(connectivityManager)) {
            _isLoading.postValue(true)
            viewModelScope.launch {
                _itemsForCategory.value = fetchCategoriesAndItemsForCategoryUseCase(categoryId)
                _isLoading.postValue(false)
            }
        } else {
            _isNetworkError.postValue(true)
        }
    }
}