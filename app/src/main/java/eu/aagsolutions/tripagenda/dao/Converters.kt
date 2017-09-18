package eu.aagsolutions.tripagenda.dao

import android.arch.persistence.room.TypeConverter
import java.util.Date


/**
 * Created by aurelavramescu on 18.09.17.
 */
class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return if (date == null) {
            null
        } else {
            date!!.getTime()
        }
    }
}