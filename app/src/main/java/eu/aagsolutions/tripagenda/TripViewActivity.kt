package eu.aagsolutions.tripagenda

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import eu.aagsolutions.tripagenda.dao.AppDatabase
import eu.aagsolutions.tripagenda.services.TripService
import kotlinx.android.synthetic.main.activity_trip_view.eventsList

class TripViewActivity : AppCompatActivity() {

    private val tripService: TripService = TripService(AppDatabase.getDatabase(this)!!)

    private val arrayList = ArrayList<String>()

    private var adapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_view)
        val tripId = intent.getSerializableExtra("tripId") as String
        val trip = tripService.findById(tripId)
        val events = tripService.findEventsByTripId(tripId)
        events.forEach { e ->  arrayList.add(e.point.address) }
        adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, arrayList)
        eventsList.adapter = adapter

    }
}
