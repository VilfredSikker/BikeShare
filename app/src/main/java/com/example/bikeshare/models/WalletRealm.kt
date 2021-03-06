package com.example.bikeshare.models

import io.realm.Realm
import io.realm.kotlin.where

open class WalletRealm {
    private val realm = Realm.getDefaultInstance()

    fun createWallet() {
        this.realm.executeTransaction {
            val wallet = this.realm.where<Wallet>().findFirst()

            if (wallet == null) {
                val newWallet = Wallet()
                this.realm.copyToRealm(newWallet)
            }
        }
    }

    fun getWallet(): Wallet? {
        return this.realm.where<Wallet>().findFirst()
    }

    fun withdrawMoney(amount: Double) {
        this.realm.executeTransaction {
            val wallet = this.realm.where<Wallet>().findFirst()

            wallet!!.balance -= amount
        }
    }

    fun depositMoney(amount: Double) {
        this.realm.executeTransaction {
            val wallet = this.realm.where<Wallet>().findFirst()

            wallet!!.balance += amount
        }
    }
}