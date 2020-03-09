package com.example.bikesharev3.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bikesharev3.R
import com.example.bikesharev3.adapters.RideAdapter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

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
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupRecyclerView()
    }

    override fun onResume() {
        this.recyclerView.adapter = RideAdapter()
        super.onResume()
    }

    private fun setupRecyclerView(){
        this.viewAdapter = RideAdapter()
        this.viewManager = LinearLayoutManager(activity)

        this.recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
