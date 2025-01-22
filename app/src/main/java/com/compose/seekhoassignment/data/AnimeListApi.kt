package com.compose.seekhoassignment.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeListApi {
    @GET("top/anime")
    suspend fun fetchAnimeList(): Response<AnimeListResponse>

    @GET("anime/{anime_id}")
    suspend fun fetchAnimeDetails(@Path("anime_id") animeId: Int): Response<AnimeDetailsResponse>
}