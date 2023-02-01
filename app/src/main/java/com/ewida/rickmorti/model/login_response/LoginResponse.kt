package com.ewida.rickmorti.model.login_response

data class LoginResponse(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)