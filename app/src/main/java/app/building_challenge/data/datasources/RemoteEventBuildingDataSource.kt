package app.building_challenge.data.datasources

import app.building_challenge.app.network.Api
import app.building_challenge.data.models.Category
import app.building_challenge.data.models.Item
import javax.inject.Inject

class RemoteEventBuildingDataSource @Inject constructor(
    private val eventBuildingService: Api
) : EventBuildingDataSource {
    override suspend fun getCategories(): List<Category> {
        return eventBuildingService.getCategories()
    }

    override suspend fun getItemsForCategory(categoryId: Int): List<Item> {
        return eventBuildingService.getItemsForCategory(categoryId)
    }
}