package com.example.bikeshare.viewmodel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bikeshare.R
import com.example.bikeshare.viewmodel.fragments.adapters.RideAdapter
import com.example.bikeshare.models.Ride
import kotlinx.android.synthetic.main.fragment_ride_list.*

class RideListFragment : Fragment() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_ride_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
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
        println(ride.bikeName)
    }
}
