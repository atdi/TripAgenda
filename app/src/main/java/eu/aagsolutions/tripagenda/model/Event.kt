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
        val id: Long?,
        @Expose
        @Embedded(prefix = "loc_")
        val point: GeoPoint,
        @Expose
        val startDateTime: Date,
        @Expose
        val duration: Int,
        @Expose
        val tripId: Long)
