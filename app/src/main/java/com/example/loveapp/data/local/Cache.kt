package com.example.loveapp.data.local

import android.graphics.Bitmap

interface Cache {
    val size: Int

    operator fun set(key: String, value: Bitmap)

    operator fun get(key: String): Bitmap?

    fun remove(key: String)

    fun clear()
}