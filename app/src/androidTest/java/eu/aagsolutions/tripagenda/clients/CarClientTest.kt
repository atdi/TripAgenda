package eu.aagsolutions.tripagenda.clients

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Created by avramesc on 9/7/17.
 */
@RunWith(AndroidJUnit4::class)
class CarClientTest {

    val appContext = InstrumentationRegistry.getTargetContext()

    @Test
    fun getCarLocation() {
        val carClient = CarClient(appContext)
        val geoPoint = carClient.getCarLocation()
        assertNotNull(geoPoint)
    }

}