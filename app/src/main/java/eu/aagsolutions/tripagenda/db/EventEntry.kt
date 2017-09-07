package eu.aagsolutions.tripagenda.db

import android.provider.BaseColumns

/**
 * Created by aurelavramescu on 07.09.17.
 */
object EventEntry : BaseColumns {

    val TABLE_NAME = "event"
    val COLUMN_NAME_ADDRESS = "address"
    val COLUMN_NAME_LAT = "lat"
    val COLUMN_NAME_LON = "lon"
    val COLUMN_NAME_START_TIME = "start_time"
    val COLUMN_NAME_DURATION = "duration"

}