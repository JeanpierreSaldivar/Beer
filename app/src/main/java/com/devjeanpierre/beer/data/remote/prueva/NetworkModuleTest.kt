package com.devjeanpierre.beer.data.remote.prueva

import com.devjeanpierre.beer.data.remote.BeerApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleTest {

    private const val baseBeerApiUrl = "http://185.166.215.240/api/v1/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(baseBeerApiUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideQuoteApiClient(retrofit: Retrofit): TestApiClient {
        return retrofit.create(TestApiClient::class.java)
    }
}