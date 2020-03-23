package com.example.bikeshare.models

import android.graphics.Picture
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Bike(
    @PrimaryKey var id: Long = 0,
    @Required var bikeType: String = "",
    @Required var priceHour: Float = 0F,
    @Required var picture: Picture
    ) : RealmObject() {
    
}