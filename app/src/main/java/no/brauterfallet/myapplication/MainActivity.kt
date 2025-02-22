package no.brauterfallet.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import no.brauterfallet.myapplication.di.appModule
import no.brauterfallet.myapplication.ui.screens.HomeScreen
import no.brauterfallet.myapplication.ui.screens.MiscellaneousScreen
import no.brauterfallet.myapplication.ui.screens.SearchScreen
import no.brauterfallet.myapplication.ui.theme.ReisTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@MainActivity)
                modules(appModule)
            }
        }

        setContent {
            ReisTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                text = "Reis",
                                style = MaterialTheme.typography.titleLarge
                            )
                        })
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = false,
                                onClick = { navController.navigate(Home) },
                                icon = { Icon(Icons.Default.Home, null) }
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { navController.navigate(Search) },
                                icon = { Icon(Icons.Default.Search, null) }
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { navController.navigate(Misc) },
                                icon = { Icon(Icons.Default.MoreHoriz, null) }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Search,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Home> { HomeScreen() }
                        composable<Search> { SearchScreen() }
                        composable<Misc> { MiscellaneousScreen() }
                    }
                }
            }
        }
    }
}

@Serializable
object Home

@Serializable
object Search

@Serializable
object Misc