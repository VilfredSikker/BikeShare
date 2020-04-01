package com.example.bikeshare.models

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where

open class BikeRealm{
    private val realm = Realm.getDefaultInstance()

    fun createBike(bike: Bike){
        bike.id = nextIndex()

        this.realm.beginTransaction()
        var newBike = realm.copyToRealm(bike)
        this.realm.commitTransaction()
    }

    fun availableBike(id:Long) : Boolean {
        val bike = getBike(id)
        if (bike != null) return bike.available
        return false
    }

    fun toggleAvailable(id:Long) {
        val bike = getBike(id)

        if (bike != null) {
            this.realm.executeTransaction {
                bike.available = !bike.available
            }
        }
    }

    fun updateBike(bike: Bike) {
        this.realm.beginTransaction()
        val realmBike = this.realm.where<Bike>().equalTo("id", bike.id).findFirst()
        realmBike!!.apply {
            available = bike.available
            priceHour = bike.priceHour
            picture = bike.picture

        }
        this.realm.commitTransaction()
    }

    fun deleteBike(id:Long) {
        realm.executeTransactionAsync {
            val bike = getBike(id)
            bike!!.deleteFromRealm()
        }
    }

    fun getBikes(): RealmResults<Bike> {
        return realm.where<Bike>().findAll()
    }

    fun getAvailabledBikes(): RealmResults<Bike> {
        return realm.where<Bike>().equalTo("available", true).findAll()
    }

    private fun getBike(id : Long) : Bike? {
        return this.realm.where<Bike>().equalTo("id", id).findFirst()
    }

    private fun nextIndex() : Long {
        val bike = this.realm.where<Bike>().sort("id", Sort.DESCENDING).findFirst()
        // ?: = Elvis operator. If the value to the left of operator is not null, return, otherwise return value to the right
        val index = bike?.id ?: 0
        return index + 1
    }
}