package com.example.bikeshare.helpers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.location.*
import java.lang.Exception
import java.util.*

open class LocationHelper(private val activity: Activity, private val context: Context) {

    companion object {
        private const val PERMISSION_ID: Int = 42
        private const val UPDATE_INTERVAL = 5000L
        private const val FASTEST_INTERVAL = 5000L
    }

    private var locationCallback: LocationCallback
    private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices
        .getFusedLocationProviderClient(context!!)

    var lat = 0.0
    var lng = 0.0

    init {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    lat = location.longitude
                    lng= location.latitude
                }
            }
        }
    }

    fun formatLocation(location: Location?) : String {
        return "lat: ${location?.latitude}, lng: ${location?.longitude}"
    }

    fun getAddress(): String {
        val geoCoder = Geocoder(this.activity, Locale.getDefault())
        val stringBuilder = StringBuilder()

        try {
            val addresses: List<Address> =
                geoCoder.getFromLocation(lat, lng, 1)

            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                stringBuilder.apply {
                    append(address.getAddressLine(0)).append("\n")
                    append(address.locality).append("\n")
                    append(address.postalCode).append("\n")
                }
            } else {
                stringBuilder.apply {
                    append("lat: $lat").append("\n")
                    append("lng: $lng").append("\n")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

    fun startLocationUpdates(){
        if (!hasPermission())
            requestPermissions()

        if (isLocationEnabled()) {
            val locationRequest = LocationRequest().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = UPDATE_INTERVAL
                fastestInterval = FASTEST_INTERVAL
            }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, null
            )
        } else {
            Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(context, intent, null)
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient
            .removeLocationUpdates(locationCallback)
    }

    private fun hasPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }

        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }
}