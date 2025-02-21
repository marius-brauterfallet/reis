package no.brauterfallet.myapplication.di

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
import no.brauterfallet.myapplication.ui.screens.HomeScreenViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeScreenViewModel)

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

    singleOf(::GeocoderDataSourceImpl) { bind<GeocoderDataSource>() }
    singleOf(::JourneyPlannerDataSourceImpl) { bind<JourneyPlannerDataSource>() }

    singleOf(::AppRepositoryImpl) { bind<AppRepository>() }
}