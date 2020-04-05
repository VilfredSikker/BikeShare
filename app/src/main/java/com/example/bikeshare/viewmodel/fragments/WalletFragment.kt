package com.example.bikeshare.viewmodel.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.bikeshare.R
import com.example.bikeshare.models.WalletRealm
import kotlinx.android.synthetic.main.fragment_wallet.*
import java.math.RoundingMode

/**
 * A simple [Fragment] subclass.
 */
class WalletFragment : Fragment() {
    private val walletRealm:WalletRealm = WalletRealm()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.wallet_deposit_button.setOnClickListener {
            this.walletRealm.depositMoney(100.0)
            this.wallet_balance.setText(round(walletRealm.getWallet()!!.balance).toString())
        }
        this.wallet_withdraw_button.setOnClickListener {
            this.walletRealm.withdrawMoney(100.0)
            this.wallet_balance.setText(round(walletRealm.getWallet()!!.balance).toString())
        }
        this.wallet_balance.setText(round(walletRealm.getWallet()!!.balance).toString())
    }

    fun round(number: Double) : Double {
        return number.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
    }
}
