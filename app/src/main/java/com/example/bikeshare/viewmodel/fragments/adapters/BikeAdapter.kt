package com.example.bikeshare.viewmodel.fragments.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bikeshare.R
import com.example.bikeshare.models.Bike
import com.example.bikeshare.models.BikeRealm

public class BikeAdapter(private var listener:(Bike)->Unit) : RecyclerView.Adapter<BikeAdapter.ViewHolder>() {
    val bikeRealm : BikeRealm = BikeRealm()

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.bike_layout, parent, false)) {
        var type : TextView = itemView.findViewById(R.id.bike_layout_type)
        var price : TextView = itemView.findViewById(R.id.bike_layout_price)
        var image : ImageView = itemView.findViewById(R.id.bike_layout_image)

        fun setOnBikeSelected(bike: Bike, listener: (Bike) -> Unit) {
            itemView.setOnClickListener {
                listener(bike)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return bikeRealm.getBikes().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bike = bikeRealm.getBikes()[position]

        if (bike != null) {
            if (bike.picture != null)
            {
                val image : Bitmap = BitmapFactory.decodeByteArray(bike.picture, 0, bike.picture!!.size)
                holder.image.setImageBitmap(image)
            }
            holder.price.text = bike.priceHour.toString()
            holder.type.text = bike.bikeType
            holder.setOnBikeSelected(bike, listener)
        }
    }
}