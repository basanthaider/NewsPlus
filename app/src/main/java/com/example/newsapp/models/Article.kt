package com.example.newsapp.models

import com.google.gson.annotations.SerializedName

data class Article(
    val title: String,
    val link: String,
    @SerializedName("image_url")
    val imgUrl: String,
    val description: String,
    @SerializedName("article_id")
    val id:String
);
