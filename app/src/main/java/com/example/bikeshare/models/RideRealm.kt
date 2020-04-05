package com.example.bikeshare.models

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where

open class RideRealm {
    private val realm = Realm.getDefaultInstance()

    fun createRide(ride: Ride){
        ride.id = nextIndex()

        this.realm.executeTransaction {
            this.realm.copyToRealm(ride)
        }
    }

    fun currentRide() : Ride? {
        return realm.where<Ride>().equalTo("active", true).findFirst()
    }

    fun updateRide(ride: Ride) {
        realm.executeTransaction {
            realm.insertOrUpdate(ride)
        }
    }

    fun deleteRide(id:Long) {
        realm.executeTransaction {
            val ride = getRide(id)
            ride!!.deleteFromRealm()
        }
    }

    fun getRides(): RealmResults<Ride> {
        return realm.where<Ride>().findAll()
    }

    fun getPreviousRides(): RealmResults<Ride> {
        return realm.where<Ride>().equalTo("active", false).findAll()
    }

    private fun getRide(id : Long) : Ride? {
        return this.realm.where<Ride>().equalTo("id", id).findFirst()
    }

    private fun nextIndex() : Long {
        val ride = this.realm.where<Ride>().sort("id", Sort.DESCENDING).findFirst()
        // ?: = Elvis operator. If the value to the left of operator is not null, return, otherwise return value to the right
        val index = ride?.id ?: 0
        return index + 1
    }
}