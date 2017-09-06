package eu.aagsolutions.tripagenda.model

/**
 * Created by aurelavramescu on 06.09.17.
 */
class Trip(val startPoint: GeoPoint, val endPoint: GeoPoint, val stopPoints: Set<GeoPoint>)
