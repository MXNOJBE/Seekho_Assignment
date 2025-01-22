package com.compose.seekhoassignment.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.compose.seekhoassignment.data.Data
import com.compose.seekhoassignment.data.viewmodel.AnimeListViewModel
import com.compose.seekhoassignment.nav.DetailScreenNav
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AnimeList(navController: NavHostController, animatedVisibilityScope: AnimatedVisibilityScope) {
    val animeListViewModel: AnimeListViewModel = koinViewModel()
    val animeListUiState by animeListViewModel.animeListUIFetchState.collectAsState()


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        when {
            animeListUiState.isLoading -> {
                CircularProgressIndicator()
            }

            animeListUiState.error != null -> {
                Text(text = animeListUiState.error ?: "Error Fetching data at AnimeList Composable")
            }

            else -> {
                val animeList = animeListUiState.result.data
                LazyColumn {
                    items(animeList.size) {
                        val anime = animeList[it]
                        AnimeItemRow(
                            anime,
                            onClick = {
                                navController.navigate(DetailScreenNav(malId = anime.malId))
                                Log.d("ID", anime.malId.toString())
                            },
                            animatedVisibilityScope
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AnimeItemRow(
    anime: Data,
    onClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Row(modifier = Modifier
        .padding(16.dp)
        .clickable {
            //Log.d("ID", anime.malId.toString())
            onClick()
        }) {
        //AsyncImage(model = anime.images.jpgType.imageUrl, contentDescription = null)
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = anime.malId.toString(),
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(key = "${anime.malId}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                ),
                fontWeight = FontWeight.Bold)
            Text(
                text = "Episodes: ${anime.episodes ?: "N/A"}",
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(key = "${anime.episodes}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                )

            )
            Text(text = "Rating: ${anime.score ?: "N/A"}")
        }
    }
}