package com.compose.seekhoassignment.ui

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.compose.seekhoassignment.data.viewmodel.AnimeListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AnimeDetailScreen(
    animeId: Int,
    animatedVisibilityScope: AnimatedContentScope
) {
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
                    Text(
                        text = animeDetail.data.malId.toString(),
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "${animeDetail.data.malId}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }
                        ),
                        fontWeight = FontWeight.Bold)
                    Text(
                        text = "Title: ${animeDetail.data.url}"
                    )
                    Text(
                        text = "Episodes: ${animeDetail.data.episodes ?: "N/A"}",
                        modifier = Modifier.sharedElement(state = rememberSharedContentState(key = "${animeDetail.data.episodes}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                       /* boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }*/
                    )
                    )
                    Text(text = "Rating: ${animeDetail.data.rating ?: "N/A"}")
                }
            }
        }
    }
}
