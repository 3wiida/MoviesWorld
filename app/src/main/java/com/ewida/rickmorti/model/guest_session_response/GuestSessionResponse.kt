package com.ewida.rickmorti.model.guest_session_response

data class GuestSessionResponse(
    val expires_at: String,
    val guest_session_id: String,
    val success: Boolean
)