package com.example.bikeshare.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Ride(
    @PrimaryKey var id: Long = 0,
    var bikeName: String = "",
    var location : String = "",
    var startTime : String = "",
    var endTime : String? = ""
): RealmObject() {

    fun changeStartTime(date: String){
        startTime = date
    }

    fun changeEndTime(date: String){
        endTime = date
    }
}