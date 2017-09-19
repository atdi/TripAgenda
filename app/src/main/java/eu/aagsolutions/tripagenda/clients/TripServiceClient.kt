package eu.aagsolutions.tripagenda.clients

import eu.aagsolutions.tripagenda.model.GeoPoint
import eu.aagsolutions.tripagenda.model.Trip
import eu.aagsolutions.tripagenda.model.TripVO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by avramesc on 9/5/17.
 */
interface TripServiceClient {

    @POST("geo/coding")
    fun buildGeoData(@Header("X-Tenant-Name") tenantId: String,
                  @Header("X-Tenant-Key") tenantKey: String,
                  @Body geoPoints: Set<GeoPoint>): Call<Set<GeoPoint>>

    @POST("geo/coding/trip")
    fun buildGeoData(@Header("X-Tenant-Name") tenantId: String,
                     @Header("X-Tenant-Key") tenantKey: String,
                     @Body trip: TripVO): Call<TripVO>

    @GET("geo/coding")
    fun buildGeoData(@Header("X-Tenant-Name") tenantId: String,
                     @Header("X-Tenant-Key") tenantKey: String,
                     @Query("address") address: String): Call<GeoPoint>

}
