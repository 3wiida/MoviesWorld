package com.ewida.rickmorti.model.genre_response_model

data class GenresResponse(
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)