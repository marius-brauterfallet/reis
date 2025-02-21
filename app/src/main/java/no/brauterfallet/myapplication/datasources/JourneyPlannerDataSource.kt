package no.brauterfallet.myapplication.datasources

import no.brauterfallet.VenueDeparturesQuery

interface JourneyPlannerDataSource {
    suspend fun getDeparturesFromVenue(venueId: String): Result<VenueDeparturesQuery.Data>
}