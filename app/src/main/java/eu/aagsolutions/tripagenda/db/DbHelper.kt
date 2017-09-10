package eu.aagsolutions.tripagenda.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * Created by aurelavramescu on 07.09.17.
 */
class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val SQL_CREATE_ENTRIES =
    "CREATE TABLE " + EventEntry.TABLE_NAME + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY," +
            EventEntry.COLUMN_NAME_ADDRESS + " VARCHAR(255)," +
            EventEntry.COLUMN_NAME_LAT + " DOUBLE," +
            EventEntry.COLUMN_NAME_LON + " DOUBLE)";

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "TripAgenda.db"
    }
}