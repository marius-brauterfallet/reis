package no.brauterfallet.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import no.brauterfallet.myapplication.di.appModule
import no.brauterfallet.myapplication.ui.components.ReisNavBar
import no.brauterfallet.myapplication.ui.components.ReisNavHost
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
                modules(appModule(LocationServices.getFusedLocationProviderClient(this@MainActivity)))
            }
        }

        setContent {
            ReisTheme {
                val navController = rememberNavController()
                val currentDestination =
                    navController.currentBackStackEntryAsState().value?.destination

                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Reis", style = MaterialTheme.typography.titleLarge)
                        })
                    },
                    bottomBar = { ReisNavBar(navController, currentDestination) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ReisNavHost(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}
