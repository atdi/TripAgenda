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
    fun save(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(vararg event: Event)

    @Query("select * from event where id = :id")
    fun findById(id: String): Event

    @Query("select * from event where trip_id = :tripId")
    fun findByTripId(tripId: String): List<Event>

    @Query("select * from event where start_date_time > :dateTime limit 1")
    fun findNextEvent(dateTime: Long): Event

}