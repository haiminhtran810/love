package com.example.loveapp

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

fun shareSocail(context: Context, url: String) {
    context?.let {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }
        startActivity(it, sendIntent, null)
    }
}