package com.ewida.rickmorti.di

import android.util.Log
import com.ewida.rickmorti.api.ApiCalls
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
            Log.d(TAG, "Interceptor : $msg")
        }
        httpClientLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder().apply {
            readTimeout(60,TimeUnit.SECONDS)
            writeTimeout(60,TimeUnit.SECONDS)
            callTimeout(5,TimeUnit.SECONDS)
            addInterceptor {chain ->  
                val newRequest=chain.request().newBuilder()
                newRequest.addHeader("Accept", "application/json")
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


}