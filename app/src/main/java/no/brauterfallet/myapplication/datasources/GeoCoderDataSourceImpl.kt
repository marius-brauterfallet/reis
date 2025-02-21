package no.brauterfallet.myapplication.datasources

import io.ktor.client.HttpClient

class GeoCoderDataSourceImpl(private val httpClient: HttpClient) : GeoCoderDataSource {
}