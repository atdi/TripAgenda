package eu.aagsolutions.tripagenda.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation

/**
 * Created by aurelavramescu on 06.09.17.
 */
@Entity
data class Trip(
        @PrimaryKey(autoGenerate = true)
        val id: Long?,
        @Embedded(prefix = "sloc_")
        val startPoint: GeoPoint,
        @Embedded(prefix = "eloc_")
        val endPoint: GeoPoint,
        @Relation(parentColumn = "id", entityColumn = "tripId")
        val events: Set<Event> = HashSet())
