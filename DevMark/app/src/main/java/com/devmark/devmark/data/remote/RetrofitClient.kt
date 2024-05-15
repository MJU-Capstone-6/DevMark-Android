package com.devmark.devmark.data.remote

import com.devmark.devmark.BuildConfig
import com.devmark.devmark.data.utils.PrettyJsonLogger
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    private val loggingInterceptor = if(BuildConfig.DEBUG){
        HttpLoggingInterceptor(PrettyJsonLogger()).setLevel(HttpLoggingInterceptor.Level.BODY)
    }else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

        return instance!!
    }
}
