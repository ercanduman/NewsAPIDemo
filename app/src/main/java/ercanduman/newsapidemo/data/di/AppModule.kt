package ercanduman.newsapidemo.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ercanduman.newsapidemo.Constants
import ercanduman.newsapidemo.data.network.NewsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Contains & creates objects which available during lifecycle of application.
 *
 * Most of these objects should have only single instance and "object" keyword provide this feature.
 *
 * SingletonComponent: A Hilt component for singleton bindings.
 *
 * @author ercan
 * @since  2/27/21
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provide NewsAPI instance via Hilt library.
     *
     * @Singleton annotation identifies a type that the injector only instantiates once.
     */
    @Provides
    @Singleton
    fun providesNewsAPI(): NewsAPI =
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
}