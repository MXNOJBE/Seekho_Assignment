package com.compose.seekhoassignment.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.compose.seekhoassignment.data.Data
import com.compose.seekhoassignment.data.viewmodel.AnimeListViewModel
import com.compose.seekhoassignment.nav.DetailScreenNav
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.AnimeList(navController: NavHostController, animatedVisibilityScope: AnimatedVisibilityScope) {
    val animeListViewModel: AnimeListViewModel = koinViewModel()
    val animeListUiState by animeListViewModel.animeListUIFetchState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Seekho Assignment",
                        fontWeight = FontWeight.Medium,
                        letterSpacing = (-0.5).sp,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                        },
              /*  navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onBackground,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },*/
            ) }
    ) {
        it->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(it)
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
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AnimeItemRow(
    anime: Data,
    onClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
            .fillMaxWidth()
            .clickable {
                //Log.d("ID", anime.malId.toString())
                onClick()
            }) {
            AsyncImage(
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(key = "${anime.malId}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                   /* boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }*/
                ),
                model = anime.images.jpgType.imageUrl,
                contentDescription = null)
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = anime.titleEnglish ?: "N/A",
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Episodes: ${anime.episodes ?: "N/A"}",
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Rating: ${anime.score ?: "N/A"}")
            }
        }
    }
}