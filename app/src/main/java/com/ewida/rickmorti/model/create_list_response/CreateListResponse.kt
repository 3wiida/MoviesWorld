package com.ewida.rickmorti.model.create_list_response

data class CreateListResponse(
    val list_id: Int,
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)