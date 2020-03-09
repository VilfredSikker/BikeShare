package com.example.bikesharev3.models

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where

open class RideRealm{
    private val realm = Realm.getDefaultInstance()

    fun createRide(ride: Ride){
        ride.id = nextIndex()
        this.realm.executeTransaction {
            var newRide = realm.copyToRealm(ride)
        }
    }

    fun activeRide() : Boolean {
        val ride = currentRide()
        if (ride != null){
            return (ride.bikeName != "" && ride.location != "")
        }
        return false
    }

    fun currentRide() : Ride? {
        return realm.where<Ride>().sort("id", Sort.DESCENDING).findFirst()
    }

    fun updateRide(ride: Ride) : Long {
        val rideRealm = findRide(ride.id)

        if (rideRealm != null) {
            realm.executeTransaction{
                this.realm.insertOrUpdate(ride)
            }
        }
        return ride.id
    }

    fun deleteRide(id:Long) {
        realm.executeTransaction {
            val ride = findRide(id)
            ride!!.deleteFromRealm()
        }
    }

    fun getRides(): RealmResults<Ride> {
        return realm.where<Ride>().findAll()
    }

    fun getRide(id: Long) {
        realm.where<Ride>().equalTo("id", id)
    }

    private fun findRide(id : Long) : Ride? {
        return realm.where<Ride>().equalTo("id", id).findFirst()
    }

    private fun nextIndex() : Long {
        return this.realm.where<Ride>().sort("id", Sort.DESCENDING).findFirst()!!.id + 1
    }
}