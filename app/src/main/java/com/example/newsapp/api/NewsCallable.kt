package com.example.newsapp.api

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {
    @GET("v2/top-headlines?country=us&apiKey=$API_KEY")
    fun getNews(
//        @Query("country")
//        countryCode: String = "us",
        @Query("category")
        category: String,
//        @Query("page")
//        pageNumber: Int = 1,
//        @Query("apiKey")
//        apiKey: String = API_KEY
    ): Call<NewsResponse>
}