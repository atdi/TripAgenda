package eu.aagsolutions.tripagenda

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_agenda.addStop
import kotlinx.android.synthetic.main.activity_agenda.btnDate
import java.util.Calendar

class AgendaActivity : AppCompatActivity() {

    private val startCalendar = Calendar.getInstance()

    private val startDateString = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            //val addedLayout: LinearLayout = findViewById(R.id.linearLayoutStopPoint) as LinearLayout
            // = LinearLayout(applicationContext)
            //addedLayout.orientation = LinearLayout.HORIZONTAL
            //addedLayout.layoutParams = LinearLayout.LayoutParams(
            //        LinearLayout.LayoutParams.MATCH_PARENT,
            //        50)
            //val destination = AutoCompleteTextView(applicationContext)
            //addedLayout.addView(destination)
            //arrayList.add(hiddenInfo)
            //adapter!!.notifyDataSetChanged()
        }

    }
}
