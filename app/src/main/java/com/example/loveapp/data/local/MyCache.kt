package com.example.loveapp.data.local

import android.graphics.Bitmap
import android.util.LruCache
import androidx.annotation.NonNull

class MyCache : Cache {

    private var lruCache = LruCache<String, Bitmap>(size)

    override val size: Int
        get() = Constant.SIZE_CACHE

    override fun set(key: String, value: Bitmap) {
        saveBitmapToCache(key, value)
    }

    override fun get(key: String): Bitmap? {
        return retrieveBitmapFromCache(key)
    }

    override fun remove(key: String) {
        lruCache.remove(key)
    }

    override fun clear() {
        lruCache.evictAll()
    }

    private fun saveBitmapToCache(key: String, @NonNull bitmap: Bitmap) {
        this.apply {
            val a = lruCache.put(key, bitmap)
            val b = ""
        }
    }

    private fun retrieveBitmapFromCache(@NonNull key: String): Bitmap? {
        this.lruCache.get(key)?.let {
            return it
        }
        return null
    }


    companion object {
        fun newInstance(): MyCache {
            return MyCache()
        }
    }
}
