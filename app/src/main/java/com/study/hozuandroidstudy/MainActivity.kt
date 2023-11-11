package com.study.hozuandroidstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.study.hozuandroidstudy.api.APIService
import com.study.hozuandroidstudy.api.AllPostResponse
import com.study.hozuandroidstudy.api.PostCreateRequest
import com.study.hozuandroidstudy.api.PostResponse
import com.study.hozuandroidstudy.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var apiService : APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000") // 호스트 PC에 연결
            .addConverterFactory(GsonConverterFactory.create()) // gson => data class로
            .build()

        apiService = retrofit.create(APIService::class.java)

//        getPosts()
//        getPost(1);
//        deletePost(3);
//        createPost();
//        modifyPost(2, mutableMapOf("title" to "hello (modified)", "author" to "world (modified)", "conent" to "hello world (modified)"))
    }

    fun getPost(id : Int) {
        val call = apiService.getPost(id)
        call.enqueue(object : Callback<PostResponse>{ // enqueue == queue에 넣는다
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) { // response == 응답 객체
                // if(response.code() == 404)
                if(response.isSuccessful) {
                    val data : PostResponse? = response.body()
                    data?.let { // null이 아니면. it == data. it은 null이 아닌 거승로 취급
                        Log.d("mytag", it.result.toString())
                    }
                } else {

                }
            }

            // 아얘 접근도 못했을 때
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {

            }
        })
    }

    fun getPosts() {
        val call = apiService.getPosts()
        call.enqueue(object : Callback<AllPostResponse>{ // enqueue == queue에 넣는다
            override fun onResponse(call: Call<AllPostResponse>, response: Response<AllPostResponse>) { // response == 응답 객체
                val data : AllPostResponse? = response.body()
                data?.let { // null이 아니면. it == data. it은 null이 아닌 거승로 취급
                    Log.d("mytag", it.result.toString())
                }
            }

            // 아얘 접근도 못했을 때
            override fun onFailure(call: Call<AllPostResponse>, t: Throwable) {

            }
        })
    }

//    fun createPost() {
//        val call = apiService.createPost(mutableMapOf("title" to "android title", "author" to "android author", "content" to "android content"))
//        call.enqueue(object : Callback<StringResponse> {
//            override fun onResponse( call: Call<StringResponse>, response: Response<StringResponse>) {            }
//
//            override fun onFailure(call: Call<StringResponse>, t: Throwable) {}
//
//        })
//    }

    fun deletePost(id : Int) {
        val call = apiService.deletePost(id)
        call.enqueue(object : Callback<StringResponse> {
            override fun onResponse( call: Call<StringResponse>, response: Response<StringResponse>) {
            }
            // 네트워크가 잘못된 거 아얘 실행도 안된 것이다.
            override fun onFailure(call: Call<StringResponse>, t: Throwable) {

            }
        })
    }

    fun modifyPost(id : Int, body : MutableMap<String, Any>) {
        val call = apiService.modifyPost(id, body)
        call.enqueue(object : Callback<StringResponse> {
            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                var data : StringResponse? = response.body()
                data?.let {
                    Log.d("mytag", it.result)
                }
            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {

            }

        })
    }
}