package no.brauterfallet.myapplication.datasources

import com.apollographql.apollo.ApolloClient
import no.brauterfallet.VenueDeparturesQuery

class JourneyPlannerDataSourceImpl(
    private val apolloClient: ApolloClient
) : JourneyPlannerDataSource {
    override suspend fun getDeparturesFromVenue(venueId: String): Result<VenueDeparturesQuery.Data> {
        return runCatching {
            apolloClient.query(VenueDeparturesQuery(venueId)).execute().dataOrThrow()
        }
    }
}