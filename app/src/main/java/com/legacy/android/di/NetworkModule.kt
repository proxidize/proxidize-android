/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.di

import android.content.Context
import com.google.gson.Gson
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.legacy.android.BuildConfig
import com.legacy.android.ServerPreference
import com.legacy.android.network.NetworkService
import com.legacy.android.network.NetworkService.Companion.HOST_NAME_
import com.legacy.android.network.ServerService
import com.legacy.android.network.ServerService.Companion.HOST_NAME
import com.legacy.android.util.ApiResponseCallAdapterFactory
import com.legacy.android.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(
                when {
                    BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                    else -> HttpLoggingInterceptor.Level.NONE
                }
            )
    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()



    @Provides
    @Singleton
    fun provideNetworkServiceApi(
        okHttpClient: OkHttpClient

    ): NetworkService =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .baseUrl(HOST_NAME_)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideServerServiceApi(
        okHttpClient: OkHttpClient

    ): ServerService =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .baseUrl(HOST_NAME)
            .build()
            .create()

    @Singleton
    @Provides
    fun provideAuthPreferences(@ApplicationContext context: Context): ServerPreference =
        ServerPreference.getInstance(context)
}
