package com.example.bikesharev3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bikesharev3.R
import com.example.bikesharev3.models.RideRealm

class RideAdapter(var rides: RideRealm = RideRealm()) :
    RecyclerView.Adapter<RideAdapter.ViewHolder>() {


    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.ride_layout, parent, false)) {
        var bikeName : TextView = itemView.findViewById(R.id.ride_layout_bike_name)
        var location : TextView = itemView.findViewById(R.id.ride_layout_location)
        var startTime : TextView = itemView.findViewById(R.id.ride_layout_start_time)
        var endTime : TextView = itemView.findViewById(R.id.ride_layout_end_time)
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
        println("onBindViewHolder: $itemCount")
        holder.bikeName.text = ride?.bikeName
        holder.location.text = ride?.location
        holder.startTime.text = ride?.startTime.toString()
        holder.endTime.text = ride!!.endTime.toString()
    }
}