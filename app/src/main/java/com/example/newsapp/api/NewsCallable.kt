package com.example.newsapp.api

import com.example.newsapp.models.NewsModel
import com.example.newsapp.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {
    @GET("news?apikey=$API_KEY")
    fun getNews(
        @Query("category") category: String ,
        @Query("country") country: String ,
    ): Call<NewsModel>
}