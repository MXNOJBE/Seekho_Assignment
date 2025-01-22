package com.compose.seekhoassignment.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.seekhoassignment.data.AnimeDetailUIState
import com.compose.seekhoassignment.data.AnimeListRepository
import com.compose.seekhoassignment.data.AnimeListUIState
import com.compose.seekhoassignment.data.NetworkResult
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AnimeListViewModel(
    private val animeListRepository: AnimeListRepository,
): ViewModel() {

    val animeListUIFetchState = MutableStateFlow(AnimeListUIState())

    val animeDetailUIFetchState = MutableStateFlow(AnimeDetailUIState())


    init {
        animeList()
    }

    private fun animeList(){
        animeListUIFetchState.value = AnimeListUIState(isLoading = true)
        viewModelScope.launch {
            val animeListResult = async { animeListRepository.getAnimeList() }
            when (
                val animeListResponse = animeListResult.await()
            ) {
                is NetworkResult.Error -> {
                    animeListUIFetchState.value = AnimeListUIState(isLoading = true, error = animeListResponse.error)
                }

                is NetworkResult.Success -> {
                    animeListUIFetchState.value  = AnimeListUIState(
                        isLoading =  false,
                        result = animeListResponse.data
                    )
                }
            }
        }
    }

    fun animeDetail(animeId: Int) {
        animeDetailUIFetchState.value = AnimeDetailUIState(isLoading = true)

        viewModelScope.launch {
            val animeDetailResult = async { animeListRepository.getAnimeDetail(animeId) }
            when (
                val animeDetailResponse = animeDetailResult.await()
            ) {
                is NetworkResult.Error -> {
                    animeDetailUIFetchState.value = AnimeDetailUIState(
                        isLoading = true,
                        error = animeDetailResponse.error
                    )
                }

                is NetworkResult.Success -> {
                    animeDetailUIFetchState.value = AnimeDetailUIState(
                        isLoading = false,
                        result = animeDetailResponse.data
                    )
                }
            }
        }
    }
}