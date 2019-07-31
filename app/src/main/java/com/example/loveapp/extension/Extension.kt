package com.example.loveapp.extension

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import java.util.*
import kotlin.math.abs

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
    val valueDate = arrayListOf(0, 100, 500, 1000, 2000, 10000)
    this.apply {
        var minValue: String
        var maxValue: String
        val currentCalendar = Calendar.getInstance().timeInMillis
        val anniversaryDate = cal.timeInMillis
        val diff = abs(currentCalendar - anniversaryDate)
        val countDate = (diff / (24 * 601 * 60 * 1000)).toInt()
        progress = countDate
        when {
            countDate < 100 -> {
                minValue = valueDate[0].toString()
                maxValue = valueDate[1].toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    min = valueDate[0]
                }
                max = valueDate[1]
            }
            countDate < 500 -> {
                minValue = valueDate[1].toString()
                maxValue = valueDate[2].toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    min = valueDate[1]
                }
                max = valueDate[2]
            }
            countDate < 1000 -> {
                minValue = valueDate[2].toString()
                maxValue = valueDate[3].toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    min = valueDate[2]
                }
                max = valueDate[3]
            }
            countDate < 2000 -> {
                minValue = valueDate[3].toString()
                maxValue = valueDate[4].toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    min = valueDate[3]
                }
                max = valueDate[4]
            }
            else -> {
                minValue = valueDate[4].toString()
                maxValue = valueDate[5].toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    min = valueDate[4]
                }
                max = valueDate[5]
            }
        }
        minView.text = minValue
        maxView.text = maxValue

    }
}

fun convertStringToDate(year: Int, month: Int, dayOfMonth: Int): Calendar {
    val cal = Calendar.getInstance()
    cal.set(year, month, dayOfMonth)
    return cal
}

