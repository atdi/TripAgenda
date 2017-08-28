package eu.aagsolutions.tripagenda

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_agenda.*
import java.util.*

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
                        btnDate.text = startDateString.toString()},
                    year, month, day)
            datePickerDialog.show()
        }

    }
}
