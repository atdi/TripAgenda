package eu.aagsolutions.tripagenda.services

import eu.aagsolutions.tripagenda.dao.EventDao
import eu.aagsolutions.tripagenda.dao.TripDao
import eu.aagsolutions.tripagenda.model.Trip

/**
 * Created by avramesc on 9/15/17.
 */
class TripService(val tripDao: TripDao, val eventDao: EventDao) {

    fun findById(id: Long): Trip {
        return tripDao.findById(id)
    }

    fun save(trip: Trip): Trip {
        eventDao.saveAll(*trip.events.toTypedArray())
        tripDao.save(trip)
        return trip
    }
}