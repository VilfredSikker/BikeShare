package com.example.bikeshare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bikeshare.fragments.MainFragment
import io.realm.Realm
import io.realm.RealmConfiguration

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
