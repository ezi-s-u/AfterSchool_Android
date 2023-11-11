package com.study.hozuandroidstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.hozuandroidstudy.api.APIService
import com.study.hozuandroidstudy.api.Post
import com.study.hozuandroidstudy.api.PostResponse
import com.study.hozuandroidstudy.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostReadActivity : AppCompatActivity() {
    var id : Int = -1
    lateinit var apiService : APIService

    lateinit var post : Post
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_read)

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL) // 호스트 PC에 연결
            .addConverterFactory(GsonConverterFactory.create()) // gson => data class로
            .build()

        apiService = retrofit.create(APIService::class.java)

        id = intent.getIntExtra("id", -1)!!

        showDetailPost(id)

        findViewById<Button>(R.id.post_delete_btn).setOnClickListener {
            deletePost(id)
        }
        findViewById<Button>(R.id.post_modify_btn).setOnClickListener {
            val intent = Intent(this, PostWriteActivity::class.java)
            intent.putExtra("post", post)
            startActivity(intent)
        }
    }

    fun showDetailPost(id : Int) {
        val call = apiService.getPost(id)
        call.enqueue(object : Callback<PostResponse> { // enqueue == queue에 넣는다
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) { // response == 응답 객체
                // if(response.code() == 404)
                if(response.isSuccessful) {
                    val data : PostResponse? = response.body()
                    data?.let { // null이 아니면. it == data. it은 null이 아닌 거승로 취급
                        post = it.result
                        findViewById<TextView>(R.id.post_title).text = "제목 : "+data.result.title
                        findViewById<TextView>(R.id.post_author).text = "글쓴이 : "+data.result.author
                        findViewById<TextView>(R.id.post_content).text = "글내용\n\n"+data.result.content
                    }
                } else {

                }
            }

            // 아얘 접근도 못했을 때
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {

            }
        })
    }

    fun deletePost(id : Int) {
        val call = apiService.deletePost(id)
        call.enqueue(object : Callback<StringResponse> {
            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                Toast.makeText(this@PostReadActivity, "글 삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this@PostReadActivity, PostListActivity::class.java)
                finish()
//                this@PostReadActivity.startActivity(intent)

            }
            // 네트워크가 잘못된 거 아얘 실행도 안된 것이다.
            override fun onFailure(call: Call<StringResponse>, t: Throwable) {

            }
        })
    }
}