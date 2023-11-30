package app.building_challenge.app.network

import app.building_challenge.data.models.Category
import app.building_challenge.data.models.Item
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("categories.json")
    suspend fun getCategories(): List<Category>

    @GET("categories/{categoryId}.json")
    suspend fun getItemsForCategory(@Path("categoryId") categoryId: Int): List<Item>


}