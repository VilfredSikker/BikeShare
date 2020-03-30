package com.example.bikeshare.viewmodel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bikeshare.R
import kotlinx.android.synthetic.main.fragment_add_new.*

class AddNewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new, container, false)
    }

    private fun setupListeners() {
        this.start_ride_button.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.content_fragment, StartRideFragment())?.commit()
        }

        this.register_bike_button.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.content_fragment, RegisterBikeFragment())?.commit()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }
}
