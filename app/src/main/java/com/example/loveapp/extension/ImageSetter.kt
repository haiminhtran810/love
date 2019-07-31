package com.example.loveapp.extension

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.loveapp.R
import com.example.loveapp.data.local.PreferenceHelper
import com.example.loveapp.data.local.PreferenceHelper.set
import android.content.Context
import java.io.FileInputStream


class ImageSetter(var activity: Activity) {
    var takenPhotoFile: File? = null


    fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)

        intent.type = "image/*";
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        intent.putExtra("outputX", 256)
        intent.putExtra("outputY", 256)
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("return-data", true)

        activity.startActivityForResult(
            Intent.createChooser(
                intent,
                CHOOSER_TITLE
            ),
            REQUEST_PICK_PHOTO
        )
    }

    fun getImageFile(uri: Uri): File? {
        uri?.let {
            val imageUri = File(it.path)
            if (!imageUri.exists()) {
                return null
            }
            return imageUri
        }

        return null

    }

    fun getPathFromURI(contentUri: Uri, context: Context): String? {
        var path: String = ""

        context.contentResolver.query(
            contentUri, arrayOf(MediaStore.Images.Media.DATA),
            null, null, null
        )?.apply {
            val columnIndex = getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            moveToFirst()
            path = getString(columnIndex)
            close()
        }

        return path
    }

    fun getBitmapToPath(path: String): Bitmap? {
        return try {
            val f = File(path)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            BitmapFactory.decodeStream(FileInputStream(f), null, options)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(activity.contentResolver, uri)
    }

    fun popupContent(view: View, title: String, key: String? = null) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
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
                var sharePref = PreferenceHelper.defaultPrefs(activity.baseContext)
                if (!etContent.text.isNullOrBlank()) {
                    key?.let {
                        sharePref[key] = etContent.text.toString()
                    }
                }
            }
        }.show()
    }

    companion object {

        const val REQUEST_TAKE_PHOTO = 1888
        const val REQUEST_PICK_PHOTO = 1889
        const val CHOOSER_TITLE = "Pick & Crop"
        const val TEMP_FILE: String = "temp_image.jpeg"

        private const val IMAGE_LIMIT_SIZE = 5 * 1024 * 1024
        private const val IMAGE_TYPE = "image/*"
        private const val IMAGE_TYPE_JPEG = "image/jpeg"
        private const val IMAGE_TYPE_JPG = "image/jpg"
        private const val IMAGE_TYPE_PNG = "image/png"
    }
}