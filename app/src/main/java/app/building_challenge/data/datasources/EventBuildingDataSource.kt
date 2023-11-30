package app.building_challenge.data.datasources

import app.building_challenge.data.models.Category
import app.building_challenge.data.models.Item

interface EventBuildingDataSource {

    suspend fun getCategories(): List<Category>
    suspend fun getItemsForCategory(categoryId: Int): List<Item>

}