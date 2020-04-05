package com.example.bikeshare.viewmodel.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bikeshare.R
import com.example.bikeshare.helpers.TimeHelper
import com.example.bikeshare.models.Ride
import com.example.bikeshare.models.RideRealm

class RideAdapter(private var listener: (Ride) -> Unit) :
    RecyclerView.Adapter<RideAdapter.ViewHolder>() {
    private var rides : RideRealm = RideRealm()

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.ride_layout, parent, false)) {
        var bikeType : TextView = itemView.findViewById(R.id.ride_layout_bike_type)
        var startTime : TextView = itemView.findViewById(R.id.ride_layout_start_time)
        var priceHour : TextView = itemView.findViewById(R.id.ride_layout_price)

        fun setOnClickListener(ride: Ride, listener: (Ride) -> Unit){
            itemView.setOnClickListener {
                listener(ride)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return rides.getPreviousRides().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ride = rides.getPreviousRides()[position]
        if (ride != null) {
            println("onBindViewHolder: $itemCount")
            holder.bikeType.text = ride.bike?.bikeType
            holder.startTime.text = TimeHelper.formatDate(ride.startTime!!)
            holder.priceHour.text = ride.cost.toString()
            holder.setOnClickListener(ride, listener)
        }
    }
}