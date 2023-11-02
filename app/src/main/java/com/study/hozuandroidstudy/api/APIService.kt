package com.study.hozuandroidstudy.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

data class Post(val id: Int, var title:String, var author: String, val createdAt: Date, var content : String)
data class AllPostResponse( // 1
    val result : List<Post> // 2
)
data class PostResponse(val result : Post)
data class PostCreateRequest(val title : String, val author : String, val content : String)
data class StringResponse(val result : String)

interface APIService {
    @GET("/posts")
    fun getPosts() : Call<AllPostResponse> // Call<> 요 안의 타입이 응답
    // 요청 보내는

    @GET("posts/{id}")
    fun getPost(@Path("id")id : Int) : Call<PostResponse>

    @POST("posts/")
    fun createPost(@Body request : PostCreateRequest) : Call<StringResponse>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id : Int) : Call<StringResponse>
}