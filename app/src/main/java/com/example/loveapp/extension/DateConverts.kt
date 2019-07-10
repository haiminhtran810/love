package com.example.loveapp.extension

import java.util.*
import java.util.concurrent.TimeUnit

fun Date.countDays(startDate: Date, unit: TimeUnit): Long {
    val timeDiff = Calendar.getInstance().time.time - startDate.time
    return unit.convert(timeDiff, TimeUnit.MILLISECONDS)
}
