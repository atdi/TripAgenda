package eu.aagsolutions.tripagenda.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date


/**
 * Created by avramesc on 8/25/17.
 */
@Entity
class Event(
        @PrimaryKey
        val id: Int,
        val point: GeoPoint,
        val startDateTime: Date,
        val duration: Int)
