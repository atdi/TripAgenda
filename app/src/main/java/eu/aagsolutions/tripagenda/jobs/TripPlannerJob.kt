package eu.aagsolutions.tripagenda.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import eu.aagsolutions.tripagenda.clients.CarClient
import eu.aagsolutions.tripagenda.dao.AppDatabase
import eu.aagsolutions.tripagenda.services.TripService
import android.graphics.Bitmap


class TripPlannerJob : JobService() {

    private val LOG_TAG = "TripPlannerJob"

    private val tripService: TripService = TripService(AppDatabase.getDatabase(this)!!)

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.i(LOG_TAG, "Job is stopped")
        return true
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        Thread(Runnable {
            Log.i(LOG_TAG, "Job is started")
            val carClient = CarClient(this)
            val event = tripService.nextEvent(System.currentTimeMillis())
            if (event != null && !event.passed) {
                val success = carClient.setNavDestination(event.point)
                if (success) {
                    event.passed = true
                    tripService.saveEvent(event)
                }
            }
        }).start()
        return true
    }


}
