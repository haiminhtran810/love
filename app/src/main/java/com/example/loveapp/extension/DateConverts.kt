package com.example.loveapp.extension

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.loveapp.R
import java.util.*
import java.util.concurrent.TimeUnit

fun Date.countDays(startDate: Date, unit: TimeUnit): Long {
    val timeDiff = Calendar.getInstance().time.time - startDate.time
    return unit.convert(timeDiff, TimeUnit.MILLISECONDS)
}



