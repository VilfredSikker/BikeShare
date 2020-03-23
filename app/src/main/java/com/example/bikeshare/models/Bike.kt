package com.example.bikeshare.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Bike(
    @PrimaryKey var id: Long = 0,
    var bikeType: String = "",
    var priceHour: Double = 0.0,
    var available: Boolean = true,
    var picture: ByteArray? = ByteArray(0)
    ) : RealmObject()