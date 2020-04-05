package com.example.bikeshare.models

import io.realm.RealmObject

open class Wallet(
    var balance: Double = 0.0
): RealmObject()