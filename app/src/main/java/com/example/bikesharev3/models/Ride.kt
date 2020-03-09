package com.example.bikesharev3.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Ride(
    @PrimaryKey var id: Long = 0,
    var bikeName: String = "",
    var location : String = "",
    var startTime : Date = (Calendar.getInstance().time),
    var endTime : Date? = null
): RealmObject() {

    fun changeStartTime(date: Date){
        startTime = date
    }

    fun changeEndTime(date: Date){
        endTime = date
    }
}