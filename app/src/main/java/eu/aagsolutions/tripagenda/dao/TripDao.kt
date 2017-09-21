package eu.aagsolutions.tripagenda.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import eu.aagsolutions.tripagenda.model.Trip

/**
 * Created by avramesc on 9/15/17.
 */
@Dao
interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(event: Trip)

    @Query("select * from trip where id = :id")
    fun findById(id: String): Trip
}