package no.brauterfallet.myapplication.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination

@Composable
fun ReisNavBar(
    navController: NavController,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Home.route,
            onClick = { navController.navigate(Destination.Home.route) },
            icon = { Icon(Icons.Default.Home, null) }
        )
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Search.route,
            onClick = { navController.navigate(Destination.Search.route) },
            icon = { Icon(Icons.Default.Search, null) }
        )
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Misc.route,
            onClick = { navController.navigate(Destination.Misc.route) },
            icon = { Icon(Icons.Default.MoreHoriz, null) }
        )
    }
}