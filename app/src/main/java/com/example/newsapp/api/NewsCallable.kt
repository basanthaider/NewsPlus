package com.example.newsapp.api

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getNews(
        @Query("category") category: String,
        @Query("country") country: String,
    ): Call<NewsResponse>
}