package com.example.bikeshare.helpers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.location.*

open class LocationHelper {

    companion object {
        private lateinit var locationCallback: LocationCallback

        private fun updateLocation(location:Location?): LocationCallback {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    val lat = locationResult!!.lastLocation.latitude
                    val lng = locationResult!!.lastLocation.longitude

                    location?.latitude = lat
                    location?.longitude = lng
                }
            }

            return locationCallback
        }

        fun formatLocation(location: Location?) : String {
            return "lat: ${location?.latitude}, lng: ${location?.longitude}"
        }

        fun getCurrentLocation(context: Context, fusedLocationClient:FusedLocationProviderClient, activity: Activity, permission_id: Int): Location? {
            var location: Location? = null
            if (checkPermissions(context)) {
                if (isLocationEnabled(context)) {
                    fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                        var newLocation: Location? = task.result

                        if (newLocation == null) {
                            requestLocation(location, activity)
                        } else {
                            location = newLocation
                        }
                    }
                } else {
                    Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(context, intent, null)
                }
            } else {
                requestPermissions(activity, permission_id)
            }

            return location
        }

        private fun requestLocation(location: Location?, activity: Activity) {
            val locationRequest = LocationRequest().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 0
                fastestInterval = 0
                numUpdates = 1
            }

            val callBack : LocationCallback = updateLocation(location)

            var locationClient = LocationServices.getFusedLocationProviderClient(activity)
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

        fun onRequestPermissionsResult(PERMISSION_ID: Int,requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            if (requestCode == PERMISSION_ID) {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Granted. Start getting the location information
                }
            }
        }
    }
}