package com.ewida.rickmorti.model.movie_response_model

import com.ewida.rickmorti.common.Common.EMPTY_STRING
import com.ewida.rickmorti.utils.date_time_utils.DateTimeUtils
import java.util.*

data class MovieResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
){
    fun getMovieRate()=String.format(Locale.US,"%.1f",vote_average)
    fun getMovieTime()=DateTimeUtils.getMovieDuration(runtime)
    fun getMovieTypes():String{
        var result= EMPTY_STRING
        for(genre in genres){
            if(genres.size>3) if (genre.id==genres[3].id) break
            result += if(result.isBlank()) genre.name else ", ${genre.name}"
        }
        return result
    }
}

