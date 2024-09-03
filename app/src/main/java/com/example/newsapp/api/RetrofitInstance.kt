package com.example.newsapp.api

import com.example.newsapp.util.Constants.Companion.BASE_URL
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    //lazy for lazily initialized retrofit instance, means that the instant will only be created when it first exist
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: NewsCallable by lazy {
        retrofit.create(NewsCallable::class.java)
    }


}