package eu.aagsolutions.tripagenda.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.util.Date


/**
 * Created by avramesc on 8/25/17.
 */
@Entity(foreignKeys = arrayOf(ForeignKey(entity = Trip::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tripId"),
        onDelete = ForeignKey.CASCADE)))
data class Event(
        @Expose
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @Expose
        @Embedded(prefix = "loc_")
        var point: GeoPoint,
        @Expose
        var startDateTime: Date,
        @Expose
        var duration: Int,
        @Expose
        var tripId: Long)
