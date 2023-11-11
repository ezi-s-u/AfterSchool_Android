package com.study.hozuandroidstudy.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

// request
data class LoginData(val email: String, val password: String)

// response
data class MeResponse(val result: UserData)
data class UserData(val email: String, val name: String, val roles: List<String>)

interface UserAPIService {
    @POST("/api/login")
    @JvmSuppressWildcards
    fun login(@Body loginData: LoginData) : Call<StringResponse>

    @GET("/api/me")
    fun me() : Call<MeResponse>

    @POST("/api/logout")
    @JvmSuppressWildcards
    fun logout() : Call<StringResponse>

    // 필요한 엔드포인트 있는 경우 추가...
}