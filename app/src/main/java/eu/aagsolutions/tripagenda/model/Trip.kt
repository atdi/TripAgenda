package eu.aagsolutions.tripagenda.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation
import com.google.gson.annotations.Expose

/**
 * Created by aurelavramescu on 06.09.17.
 */
@Entity
data class Trip(
        @Expose
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @Expose
        @Embedded(prefix = "sloc_")
        var startPoint: GeoPoint,
        @Expose
        @Embedded(prefix = "eloc_")
        var endPoint: GeoPoint) {


    @Expose
    @Ignore
    @Relation(parentColumn = "id", entityColumn = "tripId")
    var events: Set<Event> = HashSet()
}
