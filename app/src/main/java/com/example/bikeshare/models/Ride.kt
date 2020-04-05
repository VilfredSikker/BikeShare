package com.example.bikeshare.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Ride(
    @PrimaryKey var id: Long = 0,
    var bike: Bike? = null,
    var startLocation : String = "",
    var endLocation : String = "",
    var startTime : String = "",
    var endTime : String? = "",
    var active: Boolean = true
): RealmObject() {
}