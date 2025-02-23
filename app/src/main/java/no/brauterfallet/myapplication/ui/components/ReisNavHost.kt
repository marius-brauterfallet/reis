package no.brauterfallet.myapplication.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import no.brauterfallet.myapplication.ui.screens.home.HomeScreen
import no.brauterfallet.myapplication.ui.screens.misc.MiscellaneousScreen
import no.brauterfallet.myapplication.ui.screens.search.SearchScreen

sealed class Destination(val route: String) {
    data object Home : Destination("Home")
    data object Search : Destination("Search")
    data object Misc : Destination("Misc")
}

@Composable
fun ReisNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
        modifier = modifier
    ) {
        composable(Destination.Home.route) { HomeScreen() }
        composable(Destination.Search.route) { SearchScreen() }
        composable(Destination.Misc.route) { MiscellaneousScreen() }
    }
}