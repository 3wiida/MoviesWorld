package com.ewida.rickmorti.ui.auth.login

import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.utils.result_wrapper.sendSafeApiCall
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiCalls: ApiCalls) {
    suspend fun loginUser(username: String, password: String, request_token: String) =
        sendSafeApiCall { apiCalls.loginUser(username, password, request_token) }

    suspend fun requestToken()= sendSafeApiCall { apiCalls.getRequestToken() }

    suspend fun createGuestSession()= sendSafeApiCall { apiCalls.createGuestSession() }
}