package eu.aagsolutions.tripagenda.model

import android.arch.persistence.room.ColumnInfo
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
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        val id: String,
        @Expose
        @Embedded(prefix = "sloc_")
        val startPoint: GeoPoint,
        @Expose
        @Embedded(prefix = "eloc_")
        val endPoint: GeoPoint) {


    @Expose
    @Ignore
    @Relation(parentColumn = "id", entityColumn = "trip_id")
    var events: Set<Event> = HashSet()
}
