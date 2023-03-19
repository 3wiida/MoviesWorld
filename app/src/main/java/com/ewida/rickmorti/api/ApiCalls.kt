package com.ewida.rickmorti.api

import com.ewida.rickmorti.model.cast_response_model.CastResponse
import com.ewida.rickmorti.model.common_movie_response.CommonMovieResponse
import com.ewida.rickmorti.model.genre_response_model.GenresResponse
import com.ewida.rickmorti.model.guest_session_response.GuestSessionResponse
import com.ewida.rickmorti.model.login_response.LoginResponse
import com.ewida.rickmorti.model.movie_response_model.MovieResponse
import com.ewida.rickmorti.model.request_token_response.RequestTokenResponse
import com.ewida.rickmorti.model.user_login_session.UserSessionResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCalls {

    @GET("authentication/token/new")
    suspend fun getRequestToken():RequestTokenResponse

    @POST("authentication/token/validate_with_login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("request_token") request_token:String
    ):LoginResponse

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession():GuestSessionResponse

    @POST("authentication/session/new")
    @FormUrlEncoded
    suspend fun createUserSession(
        @Field("request_token") request_token:String
    ):UserSessionResponse

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page:Int
    ):CommonMovieResponse

    @GET("/3/trending/{media_type}/{time_window}")
    suspend fun getTrendingMovies(
        @Path("media_type") mediaType:String,
        @Path("time_window") timeWindow:String,
        @Query("page") page: Int
    ):CommonMovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("page") page:Int
    ):CommonMovieResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query:String,
        @Query("page") page:Int
    ):CommonMovieResponse

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page:Int
    ):CommonMovieResponse

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("page") page: Int
    ):CommonMovieResponse

    @GET("movie/popular")
    suspend fun getCountryMovies(
        @Query("with_origin_country") countryCode:String,
        @Query("language") language: String,
        @Query("page") page:Int
    ):CommonMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId:Int
    ):MovieResponse

    @GET("genre/movie/list")
    suspend fun getMovieType():GenresResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId:Int
    ):CastResponse

}