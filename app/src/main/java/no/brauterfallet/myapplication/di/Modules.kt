package no.brauterfallet.myapplication.di

import com.apollographql.apollo.ApolloClient
import com.google.android.gms.location.FusedLocationProviderClient
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import no.brauterfallet.myapplication.datasources.GeocoderDataSource
import no.brauterfallet.myapplication.datasources.GeocoderDataSourceImpl
import no.brauterfallet.myapplication.datasources.JourneyPlannerDataSource
import no.brauterfallet.myapplication.datasources.JourneyPlannerDataSourceImpl
import no.brauterfallet.myapplication.repositories.AppRepository
import no.brauterfallet.myapplication.repositories.AppRepositoryImpl
import no.brauterfallet.myapplication.services.LocationService
import no.brauterfallet.myapplication.services.LocationServiceImpl
import no.brauterfallet.myapplication.ui.screens.home.HomeScreenViewModel
import no.brauterfallet.myapplication.ui.screens.search.SearchScreenViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun appModule(fusedLocationProviderClient: FusedLocationProviderClient) = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::SearchScreenViewModel)

    single<HttpClientEngine> { Android.create() }
    single<HttpClientConfig<AndroidEngineConfig>> { HttpClientConfig() }

    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    single {
        ApolloClient.Builder()
            .serverUrl("https://api.entur.io/journey-planner/v3/graphql")
            .build()
    }

    singleOf(::GeocoderDataSourceImpl) { bind<GeocoderDataSource>() }
    singleOf(::JourneyPlannerDataSourceImpl) { bind<JourneyPlannerDataSource>() }

    singleOf(::AppRepositoryImpl) { bind<AppRepository>() }

    singleOf(::LocationServiceImpl) { bind<LocationService>() }

    single { fusedLocationProviderClient }
}