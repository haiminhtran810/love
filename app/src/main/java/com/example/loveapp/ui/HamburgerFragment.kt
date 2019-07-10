package com.example.loveapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.loveapp.R
import java.util.*

class HamburgerFragment : Fragment() {

    lateinit var mOnChangeDateListener: ChangeDateListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hamburger, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mOnChangeDateListener.onChangeDate(Calendar.getInstance())
    }

    companion object {
        const val TAG = "HamburgerFragment"
        fun newInstance() = HamburgerFragment()
    }

}
