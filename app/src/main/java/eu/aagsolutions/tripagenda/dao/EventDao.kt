package eu.aagsolutions.tripagenda.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import eu.aagsolutions.tripagenda.model.Event


/**
 * Created by aurelavramescu on 14.09.17.
 */
@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvent(event: Event)

    @Query("select * from event where id = :id")
    fun findById(id: Long): Event

    fun findByTripId(tripId: Long): Set<Event>

}