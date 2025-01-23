package com.compose.seekhoassignment.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.compose.seekhoassignment.ui.AnimeDetailScreen
import com.compose.seekhoassignment.ui.AnimeList
import kotlinx.serialization.Serializable

@Serializable
object ListScreenNav

@Serializable
data class DetailScreenNav(
    val malId: Int,
)

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = { ListScreenNav },
        detailPane = { DetailScreenNav }
    )
    SharedTransitionLayout{
        NavHost(navController = navController, startDestination = ListScreenNav) {

            composable<ListScreenNav> {
                AnimeList(
                    navController,
                    animatedVisibilityScope = this
                )
            }

            composable<DetailScreenNav> {
                val args = it.toRoute<DetailScreenNav>()
                AnimeDetailScreen(args.malId, animatedVisibilityScope = this, navController)
            }
        }
    }
}