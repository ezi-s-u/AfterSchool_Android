package com.study.hozuandroidstudy.api

import retrofit2.Call
import retrofit2.http.GET
import java.util.Date

data class Post(val id: Int, var title:String, var author: String, val createdAt: Date, var content : String)
data class AllPostResponse( // 1
    val result : List<Post> // 2
)

interface APIService {
    @GET("/posts")
    fun getPosts() : Call<AllPostResponse> // Call<> 요 안의 타입이 응답
}