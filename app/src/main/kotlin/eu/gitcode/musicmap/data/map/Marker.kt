package eu.gitcode.musicmap.data.map

import com.mapbox.mapboxsdk.geometry.LatLng

data class Marker(val coordinates: LatLng,
                  val name: String,
                  val beginYear: Int) {
}