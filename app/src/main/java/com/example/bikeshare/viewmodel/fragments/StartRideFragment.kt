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

    private fun saveRide(bike : String, where : String) {
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

            if (bike != "" && where != ""){
                saveRide(bike, where)
            } else {
                Toast.makeText(requireContext(), "Both Bike Name and Where is required", Toast.LENGTH_LONG).show()
            }
        }
    }
}
