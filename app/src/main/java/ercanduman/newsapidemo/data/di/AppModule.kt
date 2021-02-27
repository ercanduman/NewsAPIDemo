package ercanduman.newsapidemo.data.di

import ercanduman.newsapidemo.Constants
import ercanduman.newsapidemo.data.network.NewsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Contains & creates objects which available during lifecycle of application.
 *
 * Most of these objects should have only single instance and "object" keyword provide this feature.
 *
 * @author ercan
 * @since  2/27/21
 */
object AppModule {

    fun providesNewsAPI(): NewsAPI =
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
}