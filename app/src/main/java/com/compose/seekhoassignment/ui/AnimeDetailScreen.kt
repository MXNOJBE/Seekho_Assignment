package com.compose.seekhoassignment.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.seekhoassignment.data.viewmodel.AnimeListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnimeDetailScreen(animeId: Int) {
    val animeDetailViewModel: AnimeListViewModel = koinViewModel()
    val animeDetailUiState by animeDetailViewModel.animeDetailUIFetchState.collectAsState()

    LaunchedEffect(animeId) {
        Log.d("AnimeDetail", "Fetching details for anime ID: $animeId")
        animeDetailViewModel.animeDetail(animeId)
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        when {
            animeDetailUiState.isLoading -> {
                CircularProgressIndicator()
            }

            animeDetailUiState.error != null -> {
                Text(text = animeDetailUiState.error ?: "Error Fetching Data")
            }

            else -> {
                val animeDetail = animeDetailUiState.result
                Column {
                    Text(text = "Title: ${animeDetail.data.url}")
                    Text(text = "Episodes: ${animeDetail.data.episodes ?: "N/A"}")
                    Text(text = "Rating: ${animeDetail.data.rating ?: "N/A"}")
                }
            }
        }
    }
}
