package eu.aagsolutions.tripagenda

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import eu.aagsolutions.tripagenda.adapters.PlaceArrayAdapter
import kotlinx.android.synthetic.main.activity_agenda.addStop
import kotlinx.android.synthetic.main.activity_agenda.btnDate
import java.util.Calendar

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            val myLayout = findViewById(R.id.mainLayout) as LinearLayout
            val hiddenInfo: LinearLayout = layoutInflater.inflate(R.layout.hidden, myLayout, false) as LinearLayout
            myLayout.addView(hiddenInfo)
            val removeButton = hiddenInfo.findViewWithTag<ImageButton>("Delete")
            removeButton.setOnClickListener {
                myLayout.removeView(hiddenInfo)
            }
            val autocompleteText = hiddenInfo.findViewWithTag<AutoCompleteTextView>("Destination")
            setupAutocompleteTextView(autocompleteText, mLocAutocomplete)

        }

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
}
