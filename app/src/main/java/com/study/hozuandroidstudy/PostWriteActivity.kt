package com.study.hozuandroidstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.study.hozuandroidstudy.api.APIService
import com.study.hozuandroidstudy.api.PostCreateRequest
import com.study.hozuandroidstudy.api.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostWriteActivity : AppCompatActivity() {
    lateinit var writeBtn : Button
    
    lateinit var titleEdit : EditText
    lateinit var autorEdit : EditText
    lateinit var contentEdit : EditText
    
    lateinit var apiService : APIService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_write)

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL) // 호스트 PC에 연결
            .addConverterFactory(GsonConverterFactory.create()) // gson => data class로
            .build()

        apiService = retrofit.create(APIService::class.java)

        writeBtn = findViewById<Button>(R.id.post_write_btn)
        titleEdit = findViewById(R.id.post_title)
        autorEdit = findViewById(R.id.post_author)
        contentEdit = findViewById(R.id.post_content)
        
        writeBtn.setOnClickListener {
            createPost(
                titleEdit.text.toString(),
                autorEdit.text.toString(),
                contentEdit.text.toString()
            )
        }
        
        
    }

    fun createPost(title : String, author : String, content : String) {
        val call = apiService.createPost(PostCreateRequest(title, author, content))
        call.enqueue(object : Callback<StringResponse> {
            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {   
                Toast.makeText(this@PostWriteActivity, "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                this@PostWriteActivity.finish()
            }
            
            override fun onFailure(call: Call<StringResponse>, t: Throwable) {}

        })
    }
}