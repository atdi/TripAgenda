package eu.aagsolutions.tripagenda.clients

import eu.aagsolutions.tripagenda.model.GeoLocation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by avramesc on 9/5/17.
 */
interface TripServiceClient {

    @POST("geodata")
    fun buildGeoData(@Header("X-Tenant-Name") tenantId: String,
                  @Header("X-Tenant-Key") tenantKey: String,
                  @Body routeRequest: Set<GeoLocation>): Call<Set<GeoLocation>>
}
