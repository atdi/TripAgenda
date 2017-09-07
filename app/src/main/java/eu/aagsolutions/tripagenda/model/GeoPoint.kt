package eu.aagsolutions.tripagenda.model

import com.google.gson.annotations.Expose
import java.math.BigDecimal

/**
 * Created by avramesc on 8/25/17.
 */
class GeoPoint(
        @Expose
        val address: String,
        @Expose
        val lon: BigDecimal?,
        @Expose
        val lat: BigDecimal?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeoPoint

        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        return address.hashCode()
    }
}