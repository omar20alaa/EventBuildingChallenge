package app.building_challenge.data.repository

import app.building_challenge.data.datasources.EventBuildingDataSource
import app.building_challenge.data.models.Category
import app.building_challenge.data.models.Item
import javax.inject.Inject

class EventBuildingRepository @Inject constructor(
    private val eventBuildingDataSource: EventBuildingDataSource
) {
    suspend fun getCategories(): List<Category> {
        return eventBuildingDataSource.getCategories()
    }

    suspend fun getItemsForCategory(categoryId: Int): List<Item> {
        return eventBuildingDataSource.getItemsForCategory(categoryId)
    }
}