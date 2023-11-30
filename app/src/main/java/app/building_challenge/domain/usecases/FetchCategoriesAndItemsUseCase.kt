package app.building_challenge.domain.usecases

import app.building_challenge.data.models.Category
import app.building_challenge.data.models.Item
import app.building_challenge.data.repository.EventBuildingRepository
import javax.inject.Inject

class FetchCategoriesAndItemsUseCase @Inject constructor(
    private val eventBuildingRepository: EventBuildingRepository
) {
    suspend operator fun invoke(): List<Category> {
        return eventBuildingRepository.getCategories()
    }

    suspend operator fun invoke(categoryId: Int): List<Item> {
        return eventBuildingRepository.getItemsForCategory(categoryId)
    }
}