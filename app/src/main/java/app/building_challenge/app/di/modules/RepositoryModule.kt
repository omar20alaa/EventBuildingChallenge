package app.building_challenge.app.di.modules

import app.building_challenge.app.network.Api
import app.building_challenge.data.datasources.EventBuildingDataSource
import app.building_challenge.data.datasources.RemoteEventBuildingDataSource
import app.building_challenge.data.repository.EventBuildingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEventBuildingDataSource(
        eventBuildingService: Api
    ): EventBuildingDataSource {
        return RemoteEventBuildingDataSource(eventBuildingService)
    }

    @Provides
    @Singleton
    fun provideEventBuildingRepository(
        eventBuildingDataSource: EventBuildingDataSource
    ): EventBuildingRepository {
        return EventBuildingRepository(eventBuildingDataSource)
    }
}