package eu.aagsolutions.tripagenda.dao

import android.arch.persistence.room.TypeConverter
import java.util.Date


/**
 * Created by aurelavramescu on 18.09.17.
 */
class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}