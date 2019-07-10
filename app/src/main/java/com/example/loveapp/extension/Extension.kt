package com.example.loveapp.extension

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

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



