package eu.aagsolutions.tripagenda

import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.highmobility.hmkit.Command.Command
import com.highmobility.hmkit.Error.DownloadAccessCertificateError
import com.highmobility.hmkit.Error.TelematicsError
import com.highmobility.hmkit.Manager
import com.highmobility.hmkit.Telematics.CommandCallback
import com.highmobility.hmkit.Manager.DownloadCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var serial: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(View.OnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        })

        this.openCarButton.setOnClickListener {
            Manager.getInstance().telematics.sendCommand(Command.DoorLocks.lockDoors(false), SerialHolder.serial,
                    object : CommandCallback {
                        override fun onCommandResponse(response: ByteArray?) {

                        }

                        override fun onCommandFailed(error: TelematicsError?) {
                            Log.e("Telematics", error!!.message)
                        }
                    }
            )
        }

        this.lockCarButton.setOnClickListener {
            Manager.getInstance().telematics.sendCommand(Command.DoorLocks.lockDoors(true), SerialHolder.serial,
                    object : CommandCallback {
                        override fun onCommandResponse(response: ByteArray?) {

                        }

                        override fun onCommandFailed(error: TelematicsError?) {
                            Log.e("Telematics", error!!.message)
                        }
                    }
            )
        }

        this.setNavButton.setOnClickListener {
            Manager.getInstance().telematics.sendCommand(Command.NaviDestination.setDestination(52.5091992f, 13.3881374f, "Acasa"),
                    SerialHolder.serial,
                    object : CommandCallback {
                        override fun onCommandResponse(response: ByteArray?) {

                        }

                        override fun onCommandFailed(error: TelematicsError?) {
                            Log.e("Telematics", error!!.message)
                        }
                    }
            )
        }

        Manager.getInstance().initialize(
                "dGVzdApE/1TBmAxLG7ndHPe9QkJlws2llGkwdxqczmxWj0ELHHNYwHgaVggoTIF3DqDSOk/mPYa/SE4qNHbBt5vgQZgaat/iG7m9GnPv6PVvUEk/bTOEt1QCBL7QNLg4ao0ilmy3N3QjWD0RU+tIsNAW1aOtXMzWFpfMcTS7iSe/6Pd137AdIQ1MW7OwqLGnw7TmF0ozBmiV",
                "EzYlJoOnpYuBwd7plPNDXU06z04SJ8y7RN/Ceoc6mPY=",
                "7I/W4NGRhh/0crglPVYXuJZgHPFQo7wiAVv/aygWKrIpfMRqxfQHlNLUx4VLapvIlT3Sawy5Ie17vj+KUREkZQ==",
                getApplicationContext()
        )
        Manager.getInstance().downloadCertificate("1dQWwEnyagGuKZFGWvDf7oTuCaTJT20vZkQcCcqsSHNcoDjtJXBq-0KV46LE1HtExvIYUjh8rqEFdLK41Pu6gxdt4CrG8NmnpHwKQ-Cn6X5Tvxx3mV9mEJXSJYkizq-raQ",
                object : DownloadCallback {
                    override fun onDownloaded(serial: ByteArray) {
                        SerialHolder.serial = serial
                        Log.i("Success", "bolocks")
                    }

                    override fun onDownloadFailed(error: DownloadAccessCertificateError) {
                        Log.e("Download cert error", error.message)
                    }
                })

    }

}

object SerialHolder {
    var serial: ByteArray? = null
}
