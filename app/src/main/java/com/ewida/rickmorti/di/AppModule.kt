package com.ewida.rickmorti.di

import android.os.Bundle
import android.util.Log
import com.ewida.rickmorti.R
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.common.Common
import com.ewida.rickmorti.common.Common.API_TOKEN
import com.ewida.rickmorti.common.Common.BASE_URL
import com.ewida.rickmorti.common.Common.TAG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideClient():OkHttpClient{
        val httpClientLoggingInterceptor=HttpLoggingInterceptor{ msg ->
            Log.i("logInterceptor", "Interceptor : $msg")
        }
        httpClientLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder().apply {
            addInterceptor {chain ->  
                val newRequest=chain.request().newBuilder()
                newRequest.addHeader("Accept", "application/json")
                newRequest.addHeader("Authorization","Bearer $API_TOKEN")
                chain.proceed(newRequest.build())
            }
            addInterceptor(httpClientLoggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client:OkHttpClient):Retrofit{
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()
    }

    @Provides
    @Singleton
    fun provideApiCalls(retrofit:Retrofit):ApiCalls{
        return retrofit.create(ApiCalls::class.java)
    }

    @Provides
    @Singleton
    fun provideBundle():Bundle{
        return Bundle()
    }

}