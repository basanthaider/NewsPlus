package com.example.newsapp.models

import com.google.gson.annotations.SerializedName

data class NewsModel(
    val results: ArrayList<Article>
);

data class Article(
    val title: String,
    val link: String,
    @SerializedName("image_url")
    val imgUrl: String,
    val description: String,
);