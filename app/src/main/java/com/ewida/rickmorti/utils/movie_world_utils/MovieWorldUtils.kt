package com.ewida.rickmorti.utils.movie_world_utils

import java.text.SimpleDateFormat
import java.util.*

fun isSessionValid(expiration:String):Boolean{
    if(expiration.isNotEmpty()) {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ar","eg"))
        val expiryDate = dateFormatter.parse(expiration)
        if (expiryDate != null) {
            return System.currentTimeMillis() <= expiryDate.time
        }
    }
    return true
}