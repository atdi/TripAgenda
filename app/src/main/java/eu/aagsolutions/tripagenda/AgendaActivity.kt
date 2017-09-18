package eu.aagsolutions.tripagenda

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.GsonBuilder
import eu.aagsolutions.tripagenda.adapters.PlaceArrayAdapter
import eu.aagsolutions.tripagenda.clients.TripServiceClient
import eu.aagsolutions.tripagenda.dao.AppDatabase
import eu.aagsolutions.tripagenda.model.Event
import eu.aagsolutions.tripagenda.model.GeoPoint
import eu.aagsolutions.tripagenda.model.Trip
import kotlinx.android.synthetic.main.activity_agenda.addStop
import kotlinx.android.synthetic.main.activity_agenda.btnCollectDestinations
import kotlinx.android.synthetic.main.activity_agenda.btnDate
import kotlinx.android.synthetic.main.activity_agenda.mainLayout
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.HashSet
import java.util.UUID
import java.util.concurrent.TimeUnit


class AgendaActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private val LOG_TAG = "AgendaActivity"

    private val startCalendar = Calendar.getInstance()

    private val startDateString = StringBuilder()

    private val GOOGLE_API_CLIENT_ID = 0

    private val BOUNDS_BERLIN = LatLngBounds(
            LatLng(52.373688, 13.700091), LatLng(52.708610, 13.050524))

    private var mLocAutocomplete: PlaceArrayAdapter? = null

    private var mGoogleApiClient: GoogleApiClient? = null

    private val locations = HashSet<LinearLayout>()

    private var tripServiceClient: TripServiceClient? = null

    private val db: AppDatabase? = AppDatabase.getDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTripServiceClient()

        // Init maps
        mGoogleApiClient = GoogleApiClient.Builder(this@AgendaActivity)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build()
        mLocAutocomplete = PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_BERLIN, null)

        setContentView(R.layout.activity_agenda)

        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        startCalendar.set(year, month, day, hour, minute)
        startDateString.append(day).append("/").append(month).append("/").append(year)
        this.btnDate.text = startDateString.toString()

        this.btnDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this@AgendaActivity,
                    DatePickerDialog.OnDateSetListener { view, startYear, startMonth, startDay ->
                        startCalendar.set(Calendar.YEAR, startYear)
                        startCalendar.set(Calendar.MONTH, startMonth)
                        startCalendar.set(Calendar.DAY_OF_MONTH, startDay)
                        startDateString.delete(0, startDateString.length)
                        startDateString.append(startDay)
                                .append("/")
                                .append(startMonth)
                                .append("/")
                                .append(startYear)
                        btnDate.text = startDateString.toString()
                    },
                    year, month, day)
            datePickerDialog.show()
        }

        this.addStop.setOnClickListener {
            val hiddenInfo: LinearLayout = layoutInflater.inflate(R.layout.hidden, mainLayout, false) as LinearLayout
            mainLayout.addView(hiddenInfo)
            val removeButton = hiddenInfo.findViewWithTag<ImageButton>("Delete")
            val autocompleteText = hiddenInfo.findViewWithTag<AutoCompleteTextView>("Destination")
            setupAutocompleteTextView(autocompleteText, mLocAutocomplete)
            removeButton.setOnClickListener {
                mainLayout.removeView(hiddenInfo)
            }
            locations.add(hiddenInfo)
        }
        collectDestinations()

    }

    private fun setupAutocompleteTextView(addressTextView: AutoCompleteTextView,
                                          locAutoComplete: PlaceArrayAdapter?) {
        addressTextView.threshold = 3
        addressTextView.setAdapter(locAutoComplete)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode())

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show()
    }

    override fun onConnected(p0: Bundle?) {
        mLocAutocomplete!!.setGoogleApiClient(mGoogleApiClient)
        Log.i(LOG_TAG, "Google Places API connected.")
    }

    override fun onConnectionSuspended(p0: Int) {
        mLocAutocomplete!!.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.")
    }

    private fun collectDestinations() {
        this.btnCollectDestinations.setOnClickListener {
            val stopPoints = HashSet<Event>()
            val childCount = mainLayout.getChildCount()
            val trip = Trip(UUID.randomUUID().toString(),
                    GeoPoint("Landsberger Allee 171D, 10369, Berlin", null, null),
                    GeoPoint("Landsberger Allee 171D, 10369, Berlin", null, null))
            db?.tripModel()?.save(trip)
            for(i: Int in 0 until childCount) {
                val view = mainLayout.getChildAt(i)
                if (view is LinearLayout) {
                    val textBox = view.getChildAt(1) as AutoCompleteTextView
                    val point = GeoPoint(textBox.text.toString(), null, null)
                    val arrTime = view.getChildAt(2) as EditText
                    val duration = view.getChildAt(3) as EditText
                    val timeString = arrTime.text.toString().split(":")
                    startCalendar.set(Calendar.HOUR, Integer.parseInt(timeString[0]))
                    startCalendar.set(Calendar.MINUTE, Integer.parseInt(timeString[1]))
                    val event = Event(UUID.randomUUID().toString(), point, startCalendar.time,
                            Integer.parseInt(duration.text.toString()), trip.id!!)
                    db!!.eventModel()!!.save(event)
                    stopPoints.add(event)
                }
            }

            trip.events.plus(stopPoints)
            val call = this.tripServiceClient?.buildGeoData("TOADD", "TOADD", trip)
            call?.enqueue(object : Callback<Trip> {
                override fun onResponse(call: Call<Trip>?, response: Response<Trip>?) {
                    response?.body()
                }

                override fun onFailure(call: Call<Trip>?, t: Throwable?) {
                    call?.cancel()
                }

            })

        }


    }

    private fun initTripServiceClient() {
        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build()
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val restClient = Retrofit.Builder()
                .baseUrl("https://trip-service.herokuapp.com/api/")
                //.baseUrl("http://192.168.0.5:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
        tripServiceClient = restClient!!.create(TripServiceClient::class.java!!)
    }

}
