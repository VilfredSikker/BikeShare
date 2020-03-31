package com.example.bikeshare.viewmodel.fragments


import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bikeshare.R
import com.example.bikeshare.helpers.LocationHelper
import com.example.bikeshare.models.Bike
import com.example.bikeshare.models.Ride
import com.example.bikeshare.models.RideRealm
import com.example.bikeshare.viewmodel.fragments.adapters.BikeAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_start_ride.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass.
 */
class StartRideFragment : Fragment() {
    private var rideRealm: RideRealm = RideRealm()

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var selectedBike: Bike? = null

    private lateinit var currentLocation:Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val PERMISSION_ID = 42


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_ride, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        getCurrentLocation()
    }

    private fun getCurrentLocation(){
        var context = this.requireContext()
        if (LocationHelper.checkPermissions(context)) {
            if (LocationHelper.isLocationEnabled(context)) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result

                    if (location == null) {
                        requestLocation()
                    } else {
                        currentLocation = location
                    }

                }
            } else {
                Toast.makeText(this.requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            LocationHelper.requestPermissions(this.requireActivity(), PERMISSION_ID)
        }
    }

    private fun requestLocation() {
        val locationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 0
            fastestInterval = 0
            numUpdates = 1
        }

        val callBack : LocationCallback = LocationHelper.updateLocation(currentLocation)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        fusedLocationClient!!.requestLocationUpdates(locationRequest, callBack, Looper.myLooper())
    }

    private fun setupListeners() {
        this.start_ride_start_button.setOnClickListener { saveRide() }
        this.start_ride_cancel_button.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.content_fragment, AddNewFragment())?.commit()
        }
    }

    private fun setupRecyclerView() {
        this.viewAdapter = BikeAdapter { bike: Bike -> onBikeClicked(bike)}
        this.viewManager = LinearLayoutManager(activity)

        this.start_ride_available_bikes.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun onBikeClicked(bike: Bike) {
        println("Bike: $bike")
        selectedBike = bike

        var bitmap = BitmapFactory.decodeByteArray(bike.picture, 0 , bike.picture!!.size)

        this.start_ride_selected_image.setImageBitmap(bitmap)
        this.start_ride_selected_price.setText(bike.priceHour.toString())
        this.start_ride_selected_type.setText(bike.bikeType)
    }

    private fun saveRide() {
        val alreadyActiveRide = rideRealm.currentRide()
        if (selectedBike != null) {
            if (alreadyActiveRide != null) {
                Toast.makeText(this.requireContext(), "You have an active ride", Toast.LENGTH_SHORT).show()
            } else {
                val alertDialogBuilder = AlertDialog.Builder(activity).apply {
                    setTitle("Save ride?")
                    setPositiveButton("Yes") { _, _ ->
                        Toast.makeText(requireContext(), "Ride saved", Toast.LENGTH_LONG).show()

                        val ride = Ride()
                        val current = LocalDateTime.now()

                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val formatted = current.format(formatter)
                        val location = "lat: ${currentLocation.latitude}, lng: ${currentLocation.longitude}"


                        ride.startTime = formatted
                        ride.bike = selectedBike as Bike
                        ride.startLocation = location

                        rideRealm.createRide(ride)

                        fragmentManager?.beginTransaction()?.replace(R.id.content_fragment, AddNewFragment())?.commit()
                    }
                    setNegativeButton("No"){_ , _->  }
                }

                val dialog = alertDialogBuilder.create()
                dialog.show()
            }
        } else {
            Toast.makeText(this.requireContext(), "Select a bike", Toast.LENGTH_SHORT).show()
        }
    }
}
