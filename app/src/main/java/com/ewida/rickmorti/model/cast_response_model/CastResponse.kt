package com.ewida.rickmorti.model.cast_response_model

data class CastResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)