package com.ewida.rickmorti.model.created_lists_response

data class ListsResponse(
    val page: Int,
    val results: List<CreatedLists>,
    val total_pages: Int,
    val total_results: Int
)

data class CreatedLists(
    val description: String,
    val favorite_count: Int?=0,
    var id: Int,
    val iso_639_1: String?="",
    val item_count: Int,
    val list_type: String?="",
    val name: String,
    val poster_path: String?=""
)