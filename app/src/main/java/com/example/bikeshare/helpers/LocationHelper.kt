package com.example.bikeshare.helpers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.location.*
import java.util.*

open class LocationHelper(val activity: Activity) : LocationListener {
    private lateinit var locationCallback: LocationCallback
    var locationManager: LocationManager? = this.activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

    lateinit var currentLocation: Location

    private fun updateLocation(): LocationCallback {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                val lat = locationResult!!.lastLocation.latitude
                val lng = locationResult.lastLocation.longitude

                currentLocation.latitude = lat
                currentLocation.longitude = lng
            }
        }

        return locationCallback
    }

    fun formatLocation(location: Location?) : String {
        return "lat: ${location?.latitude}, lng: ${location?.longitude}"
    }

    fun getAddress(): String {
        val geoCoder = Geocoder(this.activity, Locale.getDefault())
        val addresses: List<Address>

        addresses = geoCoder.getFromLocation(
            this.currentLocation.latitude,
            this.currentLocation.longitude,
            1
        )

        return addresses[0].getAddressLine(0)
    }

    fun getCurrentLocation(context: Context, permission_id: Int) {
        if (checkPermissions(context)) {
            if (isLocationEnabled(context)) {
                try {
                    locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5f, this)
                    val location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    this.currentLocation = location

                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(context, intent, null)
            }
        } else {
            requestPermissions(activity, permission_id)
        }
    }

    fun requestLocation() {
        val locationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 0
            fastestInterval = 0
            numUpdates = 1
        }

        val callBack : LocationCallback = updateLocation()

        val locationClient = LocationServices.getFusedLocationProviderClient(activity)
        locationClient!!.requestLocationUpdates(locationRequest, callBack, Looper.myLooper())
    }

    private fun checkPermissions(context: Context): Boolean {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun isLocationEnabled(context: Context): Boolean {
        var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions(activity: Activity, PERMISSION_ID: Int) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


    override fun onLocationChanged(location: Location?) {
                
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }
}