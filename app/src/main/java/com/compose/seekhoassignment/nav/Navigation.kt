package com.compose.seekhoassignment.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
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

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ListScreenNav) {

        composable<ListScreenNav> {
            AnimeList(navController)
        }

        composable<DetailScreenNav> {
            val args = it.toRoute<DetailScreenNav>()
            AnimeDetailScreen(args.malId)
        }
    }
}