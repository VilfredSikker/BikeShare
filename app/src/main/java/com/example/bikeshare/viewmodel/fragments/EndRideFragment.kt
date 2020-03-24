package com.example.bikeshare.viewmodel.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bikeshare.R
import com.example.bikeshare.models.Ride
import com.example.bikeshare.models.RideRealm
import kotlinx.android.synthetic.main.fragment_end_ride.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class EndRideFragment : Fragment() {
    private val rideRealm: RideRealm = RideRealm()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_end_ride, container, false)
    }

    override fun onStart() {
        super.onStart()

        val currentRide = this.rideRealm.currentRide()

        if (currentRide != null){
            println("Updating current bike")
            updateViewWithCurrentBike(currentRide)
        }

        end_ride_button.setOnClickListener {
            if (currentRide != null){
                val time = Calendar.getInstance().time


                var newRide = currentRide
                newRide.endTime = time
                this.rideRealm.updateRide(newRide)
            }

            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, MainRideFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun updateViewWithCurrentBike(ride: Ride) {
        bike_name.setText(ride.bikeName)
        bike_location.setText(ride.location)
    }
}
