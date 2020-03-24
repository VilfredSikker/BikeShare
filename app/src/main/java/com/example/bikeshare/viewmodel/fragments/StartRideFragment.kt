package com.example.bikeshare.viewmodel.fragments


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.bikeshare.R
import com.example.bikeshare.models.Ride
import com.example.bikeshare.models.RideRealm
import kotlinx.android.synthetic.main.fragment_register_bike.*
import kotlinx.android.synthetic.main.fragment_start_ride.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class StartRideFragment : Fragment() {
    private var rideRealm: RideRealm = RideRealm()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_ride, container, false)
    }

    private fun saveRide(bike : String, where : String, priceHour: Double) {
        val alertDialogBuilder = AlertDialog.Builder(activity).apply {
            setTitle("Save ride?")
            setPositiveButton("Yes") { _, _ ->
                Toast.makeText(requireContext(), "Ride saved", Toast.LENGTH_LONG).show()
                val ride = Ride()
                val current = LocalDateTime.now()

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formatted = current.format(formatter)

                ride.bikeName = bike
                ride.location = where
                ride.startTime = formatted
                ride.bikePriceHour = priceHour

                rideRealm.createRide(ride)

                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment, MainRideFragment())
                    .addToBackStack(null)
                    .commit()
            }
            setNegativeButton("No"){_ , _->  }

        }

        val dialog = alertDialogBuilder.create()
        dialog.show()
    }

    override fun onStart() {
        super.onStart()
        start_ride_button.setOnClickListener {
            val bike = bike_name.text.toString()
            val where = bike_location.text.toString()
            val priceString = bike_price.text.toString()
            var priceHour = -1.0
            try {
                priceHour = priceString.toDouble()
            } catch (e: Error){
                println("Can't format $priceString to double")
            }

            if (bike != "" && where != "" && priceHour != -1.0){
                saveRide(bike, where, priceHour)
            } else {
                Toast.makeText(requireContext(), "Both Bike Name and Where is required", Toast.LENGTH_LONG).show()
            }
        }
    }
}
