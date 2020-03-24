package com.example.bikeshare

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.bikeshare.fragments.MainRideFragment
import com.example.bikeshare.fragments.RideListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_bikeshare_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bikeshare_main)

        Realm.init(this)

        var config = RealmConfiguration.Builder()
            .name("bikesharerealm.realm")
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)

        val fragment = this.supportFragmentManager
            .findFragmentById(R.id.content_fragment)

        if (fragment == null)
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.content_fragment, MainRideFragment())
                .commit()

        setupListeners()
    }

    private fun setupListeners() {
        this.bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_add_new -> {
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_fragment, MainRideFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_all_rides -> {
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_fragment, RideListFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_wallet -> {
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_fragment, MainRideFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


    }
}
