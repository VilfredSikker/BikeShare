package com.example.bikeshare.viewmodel.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bikeshare.R

import com.example.bikeshare.models.Bike
import com.example.bikeshare.models.BikeRealm
import kotlinx.android.synthetic.main.fragment_register_bike.*
import java.io.ByteArrayOutputStream


class RegisterBikeFragment : Fragment() {
    private lateinit var image: Bitmap
    private var bikeRealm : BikeRealm = BikeRealm()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_bike, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createListeners()
    }

    private fun createListeners() {
        this.register_bike_take_photo_button.setOnClickListener { dispatchTakePictureIntent() }

        this.register_bike_cancel_button.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.content_fragment, MainRideFragment())?.commit()
        }

        this.register_bike_save_button.setOnClickListener {
            saveBike()
        }
    }

    private fun saveBike(){
        val type: String = this.bike_type.text.toString()
        val price: Double? = this.bike_price.text.toString().toDoubleOrNull()
        print("Type: $type, price: $price")

        if (type != "" && price != null && price > 0.0){
            // Compress image to byteArray
            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            val bike = Bike()
            bike.bikeType = type
            bike.priceHour = price
            bike.picture = byteArray

            bikeRealm.createBike(bike)

            this.fragmentManager?.beginTransaction()?.replace(R.id.content_fragment, MainRideFragment())
        } else {
            Toast.makeText(this.requireContext(), "Couldn't save. Make sure to give a type and price", Toast.LENGTH_LONG).show()
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            bike_photo.setImageBitmap(imageBitmap)
            image = imageBitmap
        }
    }

}
