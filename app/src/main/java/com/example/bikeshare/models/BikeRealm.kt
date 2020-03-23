package com.example.bikeshare.models

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where

open class BikeRealm{
    private val realm = Realm.getDefaultInstance()

    fun createRide(bike: Bike){
        bike.id = nextIndex()

        this.realm.beginTransaction()
        var newBike = realm.copyToRealm(bike)
        this.realm.commitTransaction()
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

    fun updateRide(ride: Ride) {
        this.realm.beginTransaction()
        val realmRide = this.realm.where<Ride>().equalTo("id", ride.id).findFirst()
        realmRide!!.endTime = ride.endTime
        this.realm.commitTransaction()
    }

    fun deleteRide(id:Long) {
        realm.executeTransactionAsync {
            val ride = getRide(id)
            ride!!.deleteFromRealm()
        }
    }

    fun getRides(): RealmResults<Ride> {
        return realm.where<Ride>().findAll()
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