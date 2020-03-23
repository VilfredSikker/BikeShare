package com.example.bikeshare.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bikeshare.R
import com.example.bikeshare.adapters.RideAdapter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainRideFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private fun setupListeners() {
        this.start_ride_button.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.main_fragment, StartRideFragment())?.commit()
        }

        this.end_ride_button.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.main_fragment, EndRideFragment())?.commit()
        }

        this.register_bike_button.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.main_fragment, RegisterBikeFragment())?.commit()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()

    }

}
