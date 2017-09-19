package eu.aagsolutions.tripagenda.model

import com.google.gson.annotations.Expose
import java.util.Date

/**
 * Created by avramesc on 9/19/17.
 */
class TripVO (
        @Expose
        val id: String,
        @Expose
        val startPoint: GeoPoint,
        @Expose
        val endPoint: GeoPoint,
        @Expose
        val startDate: Date,
        @Expose
        val events: Set<Event>)