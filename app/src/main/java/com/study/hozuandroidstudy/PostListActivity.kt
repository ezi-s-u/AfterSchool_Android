package com.study.hozuandroidstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.study.hozuandroidstudy.api.APIService
import com.study.hozuandroidstudy.api.AllPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostListActivity : AppCompatActivity() {
    lateinit var apiService : APIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL) // 호스트 PC에 연결
            .addConverterFactory(GsonConverterFactory.create()) // gson => data class로
            .build()

        apiService = retrofit.create(APIService::class.java)

        showPosts()

        findViewById<FloatingActionButton>(R.id.post_write_btn).setOnClickListener {
            val intent = Intent(this, PostWriteActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        showPosts()
    }

    fun showPosts() {
        val call = apiService.getPosts()
        call.enqueue(object : Callback<AllPostResponse> {
            override fun onResponse(call: Call<AllPostResponse>, response: Response<AllPostResponse>) {
                val data : AllPostResponse? = response.body()
                data?.let {
                    // RecyclerView 세팅, 데이터 보여주기
                    val layoutManager = LinearLayoutManager(this@PostListActivity)
                    val adapter = PostListAdapter(data.result)
                    val recyclerView = findViewById<RecyclerView>(R.id.post_list)
                    recyclerView.setHasFixedSize(false) // 항목 마다의 세로 길이가 달라질 때 false, 고정은 true
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                }
            }

            // 아얘 접근도 못했을 때
            override fun onFailure(call: Call<AllPostResponse>, t: Throwable) {

            }
        })
    }
}