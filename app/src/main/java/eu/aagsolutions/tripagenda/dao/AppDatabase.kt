package eu.aagsolutions.tripagenda.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import eu.aagsolutions.tripagenda.model.Event
import eu.aagsolutions.tripagenda.model.Trip


/**
 * Created by aurelavramescu on 14.09.17.
 */
@Database(entities = arrayOf(Trip::class, Event::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    private var INSTANCE: AppDatabase? = null

    abstract fun eventModel(): EventDao

    fun getDatabase(context: Context): AppDatabase? {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "trip")
                    //                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    // To simplify the exercise, allow queries on the main thread.
                    // Don't do this on a real app!
                    .allowMainThreadQueries()
                    .build()
        }
        return INSTANCE
    }

    fun destroyInstance() {
        INSTANCE = null
    }
}