package com.ewida.rickmorti.common

import com.ewida.rickmorti.model.genre_response_model.Genre

object Common {
    const val TAG = "3wiida"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/original"
    const val API_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjNmM5ODc3MTM2ZmJjY2YzZTU1ZWQ3Y2RlODkxNGIyMyIsInN1YiI6IjYzZDk3MzU2YzE1YjU1MDBmMGI4YzAxMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wk0BCD708f_mW1yjxxlCg4Sd1Ymwn1W2EOmWfuUMfEk"
    const val EMPTY_STRING = ""
    const val DATE_PATTERN = "yyyy-MM-dd"
    var GENRES_LIST = mutableListOf<Genre>()
    var ACCOUNT_ID = -1

    //Contact Us Links
    const val FACEBOOK_APP="fb://profile/mahmoud.ewida.353"
    const val FACEBOOK_URL="fhttps://www.facebook.com/mahmoud.ewida.353/"

    const val TELEGRAM_APP="tg://openmessage?user_id=1117463830"
    const val TELEGRAM_ERROR_MSG="Telegram is not installed in your device"

    const val LINKEDIN_APP="linkedin://profile/mahmoudibr4hem"
    const val LINKEDIN_URL="https://www.linkedin.com/in/mahmoudibr4hem/"

    const val TWITTER_APP="twitter://user?user_id=1380947117569638401"
    const val TWITTER_URL="https://twitter.com/3wiida"

    const val GITHUB_URL="https://github.com/3wiida"

    const val WHATSAPP="https://api.whatsapp.com/send?phone=+201018359931"
    const val WHATSAPP_ERROR_MSG="Whatsapp is not installed in your device"
}