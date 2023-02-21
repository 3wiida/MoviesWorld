package com.ewida.rickmorti.model.trending_movie_response

data class TrendingMovieResponse(
    val page: Int,
    val results: List<TrendingMovies>,
    val total_pages: Int,
    val total_results: Int
)

