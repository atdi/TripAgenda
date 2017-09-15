package eu.aagsolutions.tripagenda.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Created by aurelavramescu on 06.09.17.
 */
@Entity
data class Trip(
        @PrimaryKey
        val id: Long,
        @Embedded(prefix = "sloc_")
        val startPoint: GeoPoint,
        @Embedded(prefix = "eloc_")
        val endPoint: GeoPoint,
        @Ignore
        val events: Set<Event>?)
