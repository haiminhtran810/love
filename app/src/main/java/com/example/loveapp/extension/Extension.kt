package com.example.loveapp.extension

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import java.util.*

fun shareSocial(context: Context, url: String) {
    context?.let {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        startActivity(it, sendIntent, null)
    }
}

fun SeekBar.setDateUI(minView: TextView, maxView: TextView, @NonNull cal: Calendar) {
    this.apply {
        minView.text = "100"
        maxView.text = "500"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            min=100
        }
        max=500
        progress = 400
    }

}

