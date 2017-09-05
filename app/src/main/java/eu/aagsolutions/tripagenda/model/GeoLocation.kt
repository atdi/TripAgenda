package eu.aagsolutions.tripagenda.model

import java.math.BigDecimal

/**
 * Created by avramesc on 8/25/17.
 */
class GeoLocation(val address: String, val lon: BigDecimal, val lat: BigDecimal) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeoLocation

        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        return address.hashCode()
    }
}