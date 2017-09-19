package eu.aagsolutions.tripagenda.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.util.Date


/**
 * Created by avramesc on 8/25/17.
 */
@Entity(foreignKeys = arrayOf(ForeignKey(entity = Trip::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("trip_id"),
        onDelete = ForeignKey.CASCADE)))
data class Event(
        @Expose
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        val id: String,
        @Expose
        @Embedded(prefix = "loc_")
        val point: GeoPoint,
        @Expose
        @ColumnInfo(name = "start_date_time")
        val startDateTime: Date,
        @Expose
        val duration: Int,
        @Expose
        @ColumnInfo(name = "trip_id", index = true)
        val tripId: String)
