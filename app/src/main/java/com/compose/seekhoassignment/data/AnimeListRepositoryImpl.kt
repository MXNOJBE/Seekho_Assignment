package com.compose.seekhoassignment.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AnimeListRepositoryImpl(
    private val animeListApi: AnimeListApi,
    private val dispatcher: CoroutineDispatcher
): AnimeListRepository {

    override suspend fun getAnimeList(): NetworkResult<AnimeListResponse> {
        return withContext(dispatcher) {
            try {
                val response = animeListApi.fetchAnimeList()
                if(response.isSuccessful){
                    val data = response.body()
                    if (data !=null){
                        Log.d("Anime List", data.toString())
                        NetworkResult.Success(data)
                    } else {
                        NetworkResult.Error("AnimeListRepositoryImpl was null")
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()?: "Fetching Error at AnimeListRepositoryImpl"
                    Log.e("Error fetching data at AnimeListRepositoryImpl", errorMessage)
                    NetworkResult.Error(errorMessage)
                }
            }
            catch (e: Exception){
                Log.e("Exception", e.message.toString())
                NetworkResult.Error("Unknown Error")
            }
        }
    }
    override suspend fun getAnimeDetail(animeId: Int): NetworkResult<AnimeDetailsResponse> {
        return withContext(dispatcher) {
            try {
                val response = animeListApi.fetchAnimeDetails(animeId)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        Log.d("API Response", "Fetched Data: $data")
                        NetworkResult.Success(data)
                    } else {
                        Log.e("API Error", "API response body is null")
                        NetworkResult.Error("Anime data is null")
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Fetching Error at AnimeListRepositoryImpl"
                    Log.e("API Error", errorMessage)
                    NetworkResult.Error(errorMessage)
                }
            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
                NetworkResult.Error("Unknown Error")
            }
        }
    }
}