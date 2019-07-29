package com.example.loveapp.ui

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.loveapp.R
import com.example.loveapp.data.local.Constant
import com.example.loveapp.data.local.PreferenceHelper
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_tootlbar.view.*
import com.example.loveapp.data.local.PreferenceHelper.get
import com.example.loveapp.extension.*
import java.util.*


class MainActivity : AppCompatActivity(), ChangeDateListener {
    private lateinit var mInterstitialAd: InterstitialAd
    private var imageSetter = ImageSetter(this)
    private var isMen = ImageType.BG
    private var sharedPre: SharedPreferences? = null
    private var isOpenFirst: Boolean? = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAds()

        sb_day.setOnTouchListener { _, _ -> true }
        baseContext?.let {
            sharedPre = PreferenceHelper.defaultPrefs(it)
        }

        handleEvent()
        getValueSharePreference()
        isOpenFirst?.let {
            if (it) {
                sequentiallyInit()
            }
        }
        val fragment = HamburgerFragment.newInstance()
        fragment.mOnChangeDateListener = this
        supportFragmentManager.beginTransaction().replace(R.id.nav_container, fragment).commit()
    }

    private fun initAds() {
        MobileAds.initialize(this, getString(R.string.id_ads_project))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        // InterstitialAd
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.ads_interstitial)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    private fun sequentiallyInit() {
        imageSetter.popupContent(tv_title_home, (tv_title_home as TextView).text.toString(), Constant.HER_NAME)
        imageSetter.popupContent(tv_content, (tv_content as TextView).text.toString(), Constant.HER_NAME)
        imageSetter.popupContent(tv_her, (tv_her as TextView).text.toString(), Constant.HER_NAME)
        imageSetter.popupContent(tv_him, (tv_him as TextView).text.toString(), Constant.HIM_NAME)
    }

    private fun getValueSharePreference() {

        sharedPre?.let {
            //image
            val pathImageHim: String? = it[IMAGE_HIM]
            val pathImageHer: String? = it[IMAGE_HER]
            val pathImageHBG: String? = it[IMAGE_BG]

            pathImageHim?.let {
                imageSetter?.apply {
                    getBitmapToPath(it)?.let { bitmap ->
                        img_him.setImageBitmap(bitmap)
                    }
                }
            }

            pathImageHer?.let {
                imageSetter?.apply {
                    getBitmapToPath(it)?.let { bitmap ->
                        img_her.setImageBitmap(bitmap)
                    }
                }
            }

            pathImageHBG?.let {
                imageSetter?.apply {
                    getBitmapToPath(it)?.let { bitmap ->
                        img_background.setImageBitmap(bitmap)
                    }
                }
            }
            //text
            val title: String? = it[Constant.TITLE_NAME]
            val titleContent: String? = it[Constant.TITLE_NAME_2]
            val himName: String? = it[Constant.HIM_NAME]
            val herName: String? = it[Constant.HER_NAME]
            tv_title_home.text = title ?: resources.getString(R.string.title_1)
            tv_content.text = titleContent ?: resources.getString(R.string.title_2)
            tv_her.text = herName ?: resources.getString(R.string.people_1)
            tv_him.text = himName ?: resources.getString(R.string.people_2)
            isOpenFirst = it[Constant.IS_OPEN_FIRST]
        }
    }

    private fun handleEvent() {
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
        toolbar?.apply {
            img_nav.setOnClickListener {
                if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.openDrawer(GravityCompat.START)
                }
            }

            img_share.setOnClickListener {
                shareSocial(context, packageName)
            }
        }

        tv_title_home.setOnClickListener {
            imageSetter.popupContent(it, (it as TextView).text.toString(), Constant.TITLE_NAME)

        }

        tv_day.setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            }
        }

        tv_content.setOnClickListener {
            imageSetter.popupContent(it, (it as TextView).text.toString(), Constant.TITLE_NAME_2)
        }

        img_her.setOnClickListener {
            isMen = ImageType.HER
            imageSetter.pickImage()
        }

        img_him.setOnClickListener {
            isMen = ImageType.HIM
            imageSetter.pickImage()
        }

        tv_her.setOnClickListener {

            imageSetter.popupContent(it, (it as TextView).text.toString(), Constant.HER_NAME)
        }

        tv_him.setOnClickListener {
            imageSetter.popupContent(it, (it as TextView).text.toString(), Constant.HIM_NAME)
        }

        lg_background.setOnClickListener {
            isMen = ImageType.BG
            imageSetter.pickImage()
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onChangeDate(calendar: Calendar) {
        sb_day.setDateUI(tv_min, tv_max, calendar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val editor = sharedPre?.edit()
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                ImageSetter.REQUEST_PICK_PHOTO -> data?.data?.apply {
                    val bitmap = imageSetter.uriToBitmap(this)
                    bitmap?.let {
                        when (isMen) {
                            ImageType.HIM -> {
                                img_him.setImageBitmap(it)
                                val a = imageSetter.getPathFromURI(this, baseContext)
                                editor?.putString(IMAGE_HIM, imageSetter.getPathFromURI(this, baseContext))
                                editor?.apply()
                            }
                            ImageType.HER -> {
                                img_her.setImageBitmap(it)
                                editor?.putString(IMAGE_HER, imageSetter.getPathFromURI(this, baseContext))
                                editor?.apply()
                            }
                            ImageType.BG -> {
                                img_background.setImageBitmap(it)
                                editor?.putString(IMAGE_BG, imageSetter.getPathFromURI(this, baseContext))
                                editor?.apply()
                            }
                        }
                    }
                }
            }
        }
    }

    enum class ImageType {
        HIM, HER, BG
    }

}