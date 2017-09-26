package eu.aagsolutions.tripagenda

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import eu.aagsolutions.tripagenda.dao.AppDatabase
import eu.aagsolutions.tripagenda.services.TripService
import kotlinx.android.synthetic.main.activity_trip_view.eventsList
import java.text.SimpleDateFormat

class TripViewActivity : AppCompatActivity() {

    private val tripService: TripService = TripService(AppDatabase.getDatabase(this)!!)

    private val arrayList = ArrayList<String>()

    private var adapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_view)
        val tripId = intent.getSerializableExtra("tripId") as String
        val trip = tripService.findById(tripId)
        arrayList.add(trip.startPoint.address)
        val events = tripService.findEventsByTripId(tripId)
        events.forEach { e ->
            run {
                val dateTime = SimpleDateFormat("dd/MM/yy hh:mm").format(e.startDateTime)
                arrayList.add(String.format("%s %s %s", e.point.address, dateTime, if (e.passed) "Yes" else "No"))
            }
        }
        arrayList.add(trip.endPoint.address)
        adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, arrayList)
        eventsList.adapter = adapter

    }
}
