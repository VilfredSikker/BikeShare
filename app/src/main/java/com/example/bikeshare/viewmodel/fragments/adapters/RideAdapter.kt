package com.example.bikeshare.viewmodel.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bikeshare.R
import com.example.bikeshare.models.Ride
import com.example.bikeshare.models.RideRealm

class RideAdapter(private var listener: (Ride) -> Unit) :
    RecyclerView.Adapter<RideAdapter.ViewHolder>() {
    private var rides : RideRealm = RideRealm()

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.ride_layout, parent, false)) {
        var bikeName : TextView = itemView.findViewById(R.id.ride_layout_bike_name)
        var startTime : TextView = itemView.findViewById(R.id.ride_layout_start_time)
        var priceHour : TextView = itemView.findViewById(R.id.bike_price)

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
        return rides.getRides().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ride = rides.getRides()[position]
        if (ride != null) {
            println("onBindViewHolder: $itemCount")
            holder.bikeName.text = ride.bikeName
            holder.startTime.text = ride.startTime
            holder.priceHour.text = ride.bikePriceHour.toString()
            holder.setOnClickListener(ride, listener)
        }

    }
}