package com.example.bikeshare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bikeshare.models.WalletRealm
import com.example.bikeshare.viewmodel.fragments.AddNewFragment
import com.example.bikeshare.viewmodel.fragments.AllRidesFragment
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_bikeshare_main.*

class MainActivity : AppCompatActivity() {
    private val walletRealm : WalletRealm = WalletRealm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bikeshare_main)

        Realm.init(this)

        var config = RealmConfiguration.Builder()
            .name("bikeshare")
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)

        walletRealm.createWallet()
        val fragment = this.supportFragmentManager
            .findFragmentById(R.id.content_fragment)

        if (fragment == null)
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.content_fragment, AddNewFragment())
                .commit()

        setupListeners()
    }

    private fun setupListeners() {
        this.bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_add_new -> {
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_fragment, AddNewFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_all_rides -> {
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_fragment, AllRidesFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_wallet -> {
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content_fragment, AddNewFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


    }
}
