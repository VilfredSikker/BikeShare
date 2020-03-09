package com.example.bikesharev3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.bikesharev3.fragments.EndRideFragment
import com.example.bikesharev3.fragments.MainFragment
import com.example.bikesharev3.fragments.StartRideFragment
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.fragment_main.*

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

        val fragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment)

        if (fragment != null)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment, MainFragment())
                .commit()
    }
}
