package com.ewida.rickmorti.common

import com.ewida.rickmorti.model.genre_response_model.Genre

object Common {
    const val TAG="3wiida"
    const val BASE_URL="https://api.themoviedb.org/3/"
    const val IMAGE_URL="https://image.tmdb.org/t/p/original"
    const val API_TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjNmM5ODc3MTM2ZmJjY2YzZTU1ZWQ3Y2RlODkxNGIyMyIsInN1YiI6IjYzZDk3MzU2YzE1YjU1MDBmMGI4YzAxMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wk0BCD708f_mW1yjxxlCg4Sd1Ymwn1W2EOmWfuUMfEk"
    const val EMPTY_STRING=""
    const val DATE_PATTERN="yyyy-MM-dd"
    var GENRES_LIST= mutableListOf<Genre>()
}