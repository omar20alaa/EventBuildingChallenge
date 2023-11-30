package app.building_challenge.data.models

data class Category(val id: Int, val title: String, val image: String)

data class Item(
    val id: Int,
    val title: String,
    val minBudget: Int,
    val maxBudget: Int,
    val avgBudget: Int,
    val image: String
)

