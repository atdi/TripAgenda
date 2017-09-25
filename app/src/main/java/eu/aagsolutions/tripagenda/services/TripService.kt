package eu.aagsolutions.tripagenda.services

import eu.aagsolutions.tripagenda.dao.AppDatabase
import eu.aagsolutions.tripagenda.dao.EventDao
import eu.aagsolutions.tripagenda.dao.TripDao
import eu.aagsolutions.tripagenda.model.Event
import eu.aagsolutions.tripagenda.model.Trip

/**
 * Created by avramesc on 9/15/17.
 */
class TripService(val tripDao: TripDao, val eventDao: EventDao) {

    constructor(db: AppDatabase) : this(db.tripModel(), db.eventModel())

    fun findById(id: String): Trip {
        return tripDao.findById(id)
    }

    fun findEventsByTripId(id: String): List<Event> {
        return eventDao.findByTripId(id)
    }

    fun update(trip: Trip) {
        tripDao.save(trip)
        trip.events.forEach { e -> eventDao.save(e) }
    }

    fun saveTrip(trip: Trip) {
        tripDao.save(trip)
    }

    fun saveEvents(events: Set<Event>) {
        eventDao.saveAll(*events.toTypedArray())
    }

    fun saveEvent(event: Event) {
        eventDao.save(event)
    }
}