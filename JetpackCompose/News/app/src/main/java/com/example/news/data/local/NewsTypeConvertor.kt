package com.example.news.data.local

import androidx.room.TypeConverter
import com.example.news.data.response.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewsTypeConvertor {

    var gson = Gson()

    @TypeConverter
    fun articleToString(article: Article): String {
        return gson.toJson(article)
    }

    @TypeConverter
    fun jsonToArticle(data: String): Article {
        val listType = object : TypeToken<Article>() {}.type
        return gson.fromJson(data,listType)
    }
}