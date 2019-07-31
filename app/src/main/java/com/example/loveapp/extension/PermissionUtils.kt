package com.example.loveapp.extension

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.os.Build
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.TargetApi
import android.app.Activity
import androidx.core.app.ActivityCompat


class PermissionUtils {
    @Suppress("PrivatePropertyName")
    private val READ_STORAGE_PERMISSION_REQUEST_CODE = 1

    private fun shouldRequestPermission(context: Context, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            false
        } else ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun requestPermission(
        activity: Activity, requestCode: Int,
        description: Int
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                READ_EXTERNAL_STORAGE
            )
        ) {

        }

        ActivityCompat.requestPermissions(
            activity,
            arrayOf(READ_EXTERNAL_STORAGE),
            READ_STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun requestReadStoragePermission(activity: Activity, description: Int) {
        requestPermission(activity, READ_STORAGE_PERMISSION_REQUEST_CODE, description)
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun shouldRequestReadStoragePermission(context: Context): Boolean {
        return shouldRequestPermission(context,READ_EXTERNAL_STORAGE)
    }
}