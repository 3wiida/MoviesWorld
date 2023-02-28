package com.ewida.rickmorti.model.top_rated_response

data class TopRatedMoviesResponse(
    val page: Int,
    val results: List<TopRatedMovie>,
    val total_pages: Int,
    val total_results: Int
)

data class TopRatedMovie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)