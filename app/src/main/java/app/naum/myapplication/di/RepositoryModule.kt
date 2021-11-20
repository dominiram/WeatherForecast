package app.naum.myapplication.di

import app.naum.myapplication.WeatherApplication
import app.naum.myapplication.network.APIService
import app.naum.myapplication.repositories.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepo(
        apiService: APIService
    ): WeatherRepo {
        return WeatherRepo(apiService)
    }
}