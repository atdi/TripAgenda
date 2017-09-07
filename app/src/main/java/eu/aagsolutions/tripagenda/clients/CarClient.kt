package eu.aagsolutions.tripagenda.clients

import android.content.Context
import android.util.Log
import com.highmobility.hmkit.Command.Command
import com.highmobility.hmkit.Command.Incoming.IncomingCommand
import com.highmobility.hmkit.Command.Incoming.VehicleLocation
import com.highmobility.hmkit.Error.DownloadAccessCertificateError
import com.highmobility.hmkit.Error.TelematicsError
import com.highmobility.hmkit.Manager
import com.highmobility.hmkit.Telematics
import eu.aagsolutions.tripagenda.model.GeoPoint
import java.math.BigDecimal

/**
 * Created by aurelavramescu on 07.09.17.
 */
class CarClient(context: Context) {

    val carManger = Manager.getInstance()

    var serialCertificate: ByteArray? = null

    init {
        carManger.initialize(
                "dGVzdApE/1TBmAxLG7ndHPe9QkJlws2llGkwdxqczmxWj0ELHHNYwHgaVggoTIF3DqDSOk/mPYa/SE4qNHbBt5vgQZgaat/iG7m9GnPv6PVvUEk/bTOEt1QCBL7QNLg4ao0ilmy3N3QjWD0RU+tIsNAW1aOtXMzWFpfMcTS7iSe/6Pd137AdIQ1MW7OwqLGnw7TmF0ozBmiV",
                "EzYlJoOnpYuBwd7plPNDXU06z04SJ8y7RN/Ceoc6mPY=",
                "7I/W4NGRhh/0crglPVYXuJZgHPFQo7wiAVv/aygWKrIpfMRqxfQHlNLUx4VLapvIlT3Sawy5Ie17vj+KUREkZQ==",
                context
        )

        var done = 0
        carManger.downloadCertificate("1dQWwEnyagGuKZFGWvDf7oTuCaTJT20vZkQcCcqsSHNcoDjtJXBq-0KV46LE1HtExvIYUjh8rqEFdLK41Pu6gxdt4CrG8NmnpHwKQ-Cn6X5Tvxx3mV9mEJXSJYkizq-raQ",
                object : Manager.DownloadCallback {
                    override fun onDownloaded(serial: ByteArray) {
                        serialCertificate = serial
                        done = 1
                    }

                    override fun onDownloadFailed(error: DownloadAccessCertificateError) {
                        Log.e("Download cert error", error.message)
                        done = -1
                    }
                })
        while (done == 0) {}
        if (done == -1) {
            throw RuntimeException("Error during certificate download")
        }
    }

    fun setNavDestination(geoPoint: GeoPoint) {
        carManger.telematics.sendCommand(Command.NaviDestination.setDestination(
                geoPoint.lat!!.toFloat(),
                geoPoint.lon!!.toFloat(), geoPoint.address),
                serialCertificate,
                object : Telematics.CommandCallback {
                    override fun onCommandResponse(response: ByteArray?) {

                    }

                    override fun onCommandFailed(error: TelematicsError?) {
                        Log.e("Telematics", error!!.message)
                    }
                }
        )
    }

    fun getCarLocation(): GeoPoint {
        var geoPoint: GeoPoint? = null
        val callback = object : Telematics.CommandCallback {
            var status = 0
            override fun onCommandResponse(response: ByteArray?) {
                Log.i("Telematics", "Stop here")
                val incomingCommand = IncomingCommand.create(response) as VehicleLocation
                geoPoint = GeoPoint("Vehicle Location",
                        BigDecimal(incomingCommand.latitude.toString()),
                        BigDecimal(incomingCommand.longitude.toString()))
                status = 1

            }

            override fun onCommandFailed(error: TelematicsError?) {
                Log.e("Telematics", error!!.message)
                status = -1
            }

            fun isDone(): Boolean {
                return status != 0
            }
        }
        carManger.telematics.sendCommand(Command.VehicleLocation.getLocation(),
                serialCertificate,
                callback
        )
        while (!callback.isDone()) {

        }

        return geoPoint!!

    }

}