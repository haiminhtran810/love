package com.example.loveapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.loveapp.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.adView
import kotlinx.android.synthetic.main.fragment_hamburger.*
import java.util.*

class HamburgerFragment : Fragment(), DatePickerInterface {
    override fun changeDate(date: Calendar) {
        tv_date_anniversary.text = date.toString()
        mOnChangeDateListener.onChangeDate(date)
    }

    lateinit var mOnChangeDateListener: ChangeDateListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hamburger, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MobileAds.initialize(context, getString(R.string.id_ads_project))
        val adRequest = AdRequest.Builder()
            .build()
        adView.loadAd(adRequest)
        lg_date.setOnClickListener {
            val dialogPicker = DatePickerFragment.newInstance()
            dialogPicker.datePickerInterface = this
            dialogPicker.show(fragmentManager, DatePickerFragment.TAG)
        }
    }

    companion object {
        const val TAG = "HamburgerFragment"
        fun newInstance() = HamburgerFragment()
    }

}
