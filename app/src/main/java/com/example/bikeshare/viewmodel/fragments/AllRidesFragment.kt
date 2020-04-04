package com.example.bikeshare.viewmodel.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bikeshare.R
import com.example.bikeshare.helpers.LocationHelper
import com.example.bikeshare.helpers.TimeHelper
import com.example.bikeshare.models.BikeRealm
import com.example.bikeshare.viewmodel.fragments.adapters.RideAdapter
import com.example.bikeshare.models.Ride
import com.example.bikeshare.models.RideRealm
import kotlinx.android.synthetic.main.fragment_all_rides.*
import kotlinx.android.synthetic.main.fragment_ride_popop.view.*

class AllRidesFragment : Fragment() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var rideRealm: RideRealm = RideRealm()
    private var bikeRealm: BikeRealm = BikeRealm()
    private lateinit var locationHelper: LocationHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        locationHelper = LocationHelper(requireActivity(), requireContext())
        return inflater.inflate(R.layout.fragment_all_rides, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupCurrentRideView()
    }

    override fun onResume() {
        super.onResume()
        locationHelper.startLocationUpdates()
    }

    private fun setupCurrentRideView() {
        val currentRide = rideRealm.currentRide()
        if (currentRide != null) {
            this.all_rides_ride_type.setText(currentRide.bike?.bikeType)
            this.all_rides_ride_start_time.setText(currentRide.startTime)
            this.all_rides_ride_price.setText(currentRide.bike?.priceHour.toString())

            this.all_rides_ride_end_ride.setOnClickListener {
                val newRide = Ride()
                newRide.startTime = currentRide.startTime
                newRide.startLocation = currentRide.startLocation
                newRide.bike = currentRide.bike
                newRide.id = currentRide.id
                newRide.active = false
                newRide.endLocation = locationHelper.getAddress()
                newRide.endTime = TimeHelper.getCurrentTime()

                rideRealm.updateRide(newRide)
                bikeRealm.toggleAvailable(newRide.bike!!.id)
                setCurrentRideInvisible()
                val itemIndex = rideRealm.getPreviousRides().indexOf(newRide)
                this.viewAdapter.notifyItemInserted(itemIndex)
            }
        } else {
            setCurrentRideInvisible()
        }
    }

    private fun setCurrentRideInvisible() {
        this.all_rides_current_ride_header.text = "No current ride"
        this.all_rides_ride_type.visibility = View.INVISIBLE
        this.all_rides_ride_start_time.visibility = View.INVISIBLE
        this.all_rides_ride_price.visibility = View.INVISIBLE
        this.all_rides_ride_price_header.visibility = View.INVISIBLE
        this.all_rides_ride_start_time_header.visibility = View.INVISIBLE
        this.all_rides_ride_type_header.visibility = View.INVISIBLE
        this.all_rides_ride_end_ride.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView(){
        this.viewAdapter = RideAdapter { ride:Ride -> onRideClicked(ride)}
        this.viewManager = LinearLayoutManager(activity)

        this.recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun onRideClicked(ride: Ride) {
        val inflater: LayoutInflater = this.layoutInflater
        val view = inflater.inflate(R.layout.fragment_ride_popop, null)

        val popup = PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        popup.elevation = 10.0F
        val slideIn = Slide()
        slideIn.slideEdge = Gravity.TOP
        popup.enterTransition = slideIn

        // Slide animation for popup window exit transition
        val slideOut = Slide()
        slideOut.slideEdge = Gravity.RIGHT
        popup.exitTransition = slideOut

        view.ride_popop_type.setText(ride.bike?.bikeType)
        view.ride_popop_image.setImageBitmap(BitmapFactory.decodeByteArray(ride.bike?.picture, 0, ride.bike?.picture!!.size))
        view.ride_popop_price.setText(ride.bike?.priceHour.toString())
        view.ride_popop_start_location.setText(ride.startLocation)
        view.ride_popop_end_location.setText(ride.endLocation)
        view.ride_popop_start_time.setText(ride.startTime)
        view.ride_popop_end_time.setText(ride.endTime)

        view.ride_popop_cancel_button.setOnClickListener { popup.dismiss() }
        view.ride_popop_delete_button.setOnClickListener {
            val itemIndex = rideRealm.getRides().indexOf(ride)
            rideRealm.deleteRide(ride.id)
            popup.dismiss()
            viewAdapter.notifyItemRemoved(itemIndex)
        }


        TransitionManager.beginDelayedTransition(ride_list_container)
        popup.showAtLocation(
            ride_list_container, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
    }
}
