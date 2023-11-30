package app.building_challenge.app.di.modules

import app.building_challenge.app.presentation.viewmodels.EventBuildingViewModel
import app.building_challenge.data.repository.EventBuildingRepository
import app.building_challenge.domain.usecases.FetchCategoriesAndItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideFetchCategoriesAndItemsForCategoryUseCase(
        eventBuildingRepository: EventBuildingRepository
    ): FetchCategoriesAndItemsUseCase {
        return FetchCategoriesAndItemsUseCase(eventBuildingRepository)
    }

    @Provides
    fun provideEventBuildingViewModel(
        fetchCategoriesAndItemsForCategoryUseCase: FetchCategoriesAndItemsUseCase
    ): EventBuildingViewModel {
        return EventBuildingViewModel(fetchCategoriesAndItemsForCategoryUseCase)
    }
}