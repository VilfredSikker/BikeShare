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
import kotlinx.android.synthetic.main.fragment_ride_list.*

class RideListFragment : Fragment() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupRecyclerView()
        return inflater.inflate(R.layout.fragment_ride_list, container, false)
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
