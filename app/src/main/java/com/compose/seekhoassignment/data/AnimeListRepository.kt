package com.compose.seekhoassignment.data

interface AnimeListRepository {
    suspend fun getAnimeList(): NetworkResult<AnimeListResponse>
    suspend fun getAnimeDetail(animeId: Int): NetworkResult<AnimeDetailsResponse>
}