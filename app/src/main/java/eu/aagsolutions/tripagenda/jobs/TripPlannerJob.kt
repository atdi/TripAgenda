package eu.aagsolutions.tripagenda.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import eu.aagsolutions.tripagenda.dao.AppDatabase
import eu.aagsolutions.tripagenda.services.TripService


class TripPlannerJob: JobService() {

    private val tripService: TripService = TripService(AppDatabase.getDatabase(this)!!)

    override fun onStopJob(p0: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
