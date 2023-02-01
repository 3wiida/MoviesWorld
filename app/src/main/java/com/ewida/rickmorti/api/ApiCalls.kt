package com.ewida.rickmorti.api

import com.ewida.rickmorti.model.login_response.LoginResponse
import com.ewida.rickmorti.model.request_token_response.RequestTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiCalls {

    @GET("authentication/token/new")
    suspend fun getRequestToken():RequestTokenResponse

    @POST("authentication/token/validate_with_login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("request_token") request_token:String
    ):LoginResponse
}