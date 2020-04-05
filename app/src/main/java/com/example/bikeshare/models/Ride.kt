package com.example.bikeshare.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Ride(
    @PrimaryKey var id: Long = 0,
    var bike: Bike? = null,
    var startLocation : String = "",
    var endLocation : String = "",
    var startTime : Date? = null,
    var endTime : Date? = null,
    var active: Boolean = true,
    var cost: Double = 0.0
): RealmObject() {
}