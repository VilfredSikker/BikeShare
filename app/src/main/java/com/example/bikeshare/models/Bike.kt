package com.example.bikeshare.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Bike(
    @PrimaryKey var id: Long = 0,
    @Required var bikeType: String = "",
    @Required var priceHour: Double = 0.0,
    var available: Boolean = true,
    @Required var picture: ByteArray? = ByteArray(0)
    ) : RealmObject()