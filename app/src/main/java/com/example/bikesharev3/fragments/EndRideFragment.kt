package com.example.bikesharev3.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bikesharev3.R
import com.example.bikesharev3.models.RideRealm
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

        if (this.rideRealm.activeRide()){
            println("Updating current bike")
            updateViewWithCurrentBike()
        }

        end_ride_button.setOnClickListener {
            val currentRide = this.rideRealm.currentRide()

            if (currentRide != null){
                currentRide.endTime = Calendar.getInstance().time

                this.rideRealm.updateRide(currentRide)
            }

            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, MainFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun updateViewWithCurrentBike() {
        println("Updating")
        bike_name.setText(this.rideRealm.currentRide()?.bikeName)
        bike_location.setText(this.rideRealm.currentRide()?.location)
    }
}
