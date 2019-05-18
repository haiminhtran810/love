package com.example.loveapp

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_tootlbar.view.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this, getString(R.string.id_ads_project))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        nav_view.setNavigationItemSelectedListener(this)
        sb_day.setOnTouchListener { _, _ -> true }
        handleEvent()
    }

    private fun handleEvent() {
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
            popupContent(it, (it as TextView).text.toString())

        }

        tv_day.setOnClickListener {

        }

        tv_content.setOnClickListener {
            popupContent(it, (it as TextView).text.toString())
        }

        img_her.setOnClickListener {

        }

        img_him.setOnClickListener {

        }

        tv_her.setOnClickListener {
            popupContent(it, (it as TextView).text.toString())
        }

        tv_him.setOnClickListener {
            popupContent(it, (it as TextView).text.toString())
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


    private fun popupContent(view: View, title: String) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val viewParent = inflater.inflate(R.layout.dialog_change_value, null)
        val etContent = viewParent.findViewById(R.id.et_content) as TextView
        val etTitle = viewParent.findViewById(R.id.tv_title) as TextView
        etTitle.text = title
        val viewDialog = builder.setView(viewParent)
        viewDialog.setPositiveButton(
            R.string.action_ok
        ) { _, _ ->
            if (!etContent.text.isNullOrBlank()) {
                (view as TextView).text = etContent.text
            }
        }.show()
    }
}
