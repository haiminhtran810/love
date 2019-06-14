package com.example.loveapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.LruCache
import android.view.*
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import com.example.loveapp.extension.ImageSetter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_tootlbar.view.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var imageSetter: ImageSetter
    private var isMen = ImageType.BG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageSetter = ImageSetter(this)

        MobileAds.initialize(this, getString(R.string.id_ads_project))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        // InterstitialAd
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.ads_interstitial)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        nav_view.setNavigationItemSelectedListener(this)
        sb_day.setOnTouchListener { _, _ -> true }
        handleEvent()

        var maxMemory =(Runtime.getRuntime().maxMemory()/1024) as Int
        var cacheSize = maxMemory/8
        /*val mMemoryCache = LruCache<String, Bitmap>(cacheSize){
        };*/
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
            imageSetter.popupContent(it, (it as TextView).text.toString())

        }

        tv_day.setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            }
        }

        tv_content.setOnClickListener {
            imageSetter.popupContent(it, (it as TextView).text.toString())
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

            imageSetter.popupContent(it, (it as TextView).text.toString())
        }

        tv_him.setOnClickListener {
            imageSetter.popupContent(it, (it as TextView).text.toString())
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ImageSetter.REQUEST_PICK_PHOTO -> data?.data?.let {
                    val bitmap = imageSetter.uriToBitmap(it)
                    bitmap?.let {
                        when (isMen) {
                            ImageType.HIM -> img_him.setImageBitmap(it)
                            ImageType.HER -> img_her.setImageBitmap(it)
                            ImageType.BG -> img_background.setImageBitmap(it)
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
