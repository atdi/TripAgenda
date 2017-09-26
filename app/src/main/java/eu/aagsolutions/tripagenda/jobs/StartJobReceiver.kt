package eu.aagsolutions.tripagenda.jobs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.job.JobScheduler
import android.app.job.JobInfo
import android.content.ComponentName



/**
 * Created by aurelavramescu on 25.09.17.
 */
class StartJobReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceComponent = ComponentName(context, TripPlannerJob::class.java)
        val builder = JobInfo.Builder(0, serviceComponent)
        builder.setMinimumLatency((1 * 1000).toLong()) // wait at least
        builder.setOverrideDeadline((3 * 1000).toLong()) // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        val jobScheduler = context?.getSystemService(JobScheduler::class.java)
        jobScheduler?.schedule(builder.build())
    }

}