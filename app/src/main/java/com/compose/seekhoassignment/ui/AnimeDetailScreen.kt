package com.compose.seekhoassignment.ui

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.compose.seekhoassignment.data.viewmodel.AnimeListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.AnimeDetailScreen(
    animeId: Int,
    animatedVisibilityScope: AnimatedContentScope,
    navController: NavHostController
) {
    val animeDetailViewModel: AnimeListViewModel = koinViewModel()
    val animeDetailUiState by animeDetailViewModel.animeDetailUIFetchState.collectAsState()
    val animeDetail = animeDetailUiState.result

    LaunchedEffect(animeId) {
        Log.d("AnimeDetail", "Fetching details for anime ID: $animeId")
        animeDetailViewModel.animeDetail(animeId)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "${animeDetail.data.titleEnglish}",
                    //fontFamily = WorkSans,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = (-0.5).sp,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onBackground,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            ) }
    ) { it ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
           /* AsyncImage(
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(key = "${animeDetail.data.malId}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    *//* boundsTransform = { _, _ ->
                     tween(durationMillis = 1000)
                 }*//*
                ),
                model = animeDetail.data.images.jpgType.imageUrl,
                contentDescription = null
            )*/
            when {
                animeDetailUiState.isLoading -> {
                    CircularProgressIndicator()
                }

                animeDetailUiState.error != null -> {
                    Text(text = animeDetailUiState.error ?: "Error Fetching Data")
                }

                else -> {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        if (animeDetail.data.trailer.url != null) {
                            animeDetail.data.trailer.url.let {
                                animeDetail.data.trailer.youtubeId?.let { it1 ->
                                    YouTubePlayer(
                                        lifecycleOwner = LocalLifecycleOwner.current,
                                        youtubeVideoId = it1
                                    )
                                }
                            }
                        } else {
                            AsyncImage(
                                modifier = Modifier.sharedElement(
                                    state = rememberSharedContentState(key = "${animeDetail.data.malId}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    /* boundsTransform = { _, _ ->
                                     tween(durationMillis = 1000)
                                 }*/
                                ),
                                model = animeDetail.data.images.webPType.largeImageUrl,
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "Episodes: ${animeDetail.data.episodes ?: "N/A"}",
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = (-0.5).sp,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.sharedElement(
                                        state = rememberSharedContentState(key = "${animeDetail.data.episodes}"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        /* boundsTransform = { _, _ ->
                                tween(durationMillis = 1000)
                            }*/
                                    )
                                )
                                Text(
                                    text = "Rating: ${animeDetail.data.score}/10",
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = (-0.5).sp,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                "Synopsis:",
                                fontWeight = FontWeight.Medium,
                                letterSpacing = (-0.5).sp,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onBackground,)
                            Spacer(modifier = Modifier.height(8.dp))
                            animeDetail.data.synopsis?.let {
                                Text(
                                    text = it,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Genres: ${
                                animeDetail.data.genres.map { it.genreName }.joinToString(", ")
                            }",

                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Producers: ${
                                animeDetail.data.producers.map { it.producerName }
                                    .joinToString(", ")
                            }",
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
    }
