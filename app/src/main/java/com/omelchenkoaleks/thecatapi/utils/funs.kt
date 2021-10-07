package com.omelchenkoaleks.thecatapi.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

fun isPermissionGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestPermissions(context: Context) {
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
        PERMISSION_CODE
    )
}

fun addImageToGallery(context: Context, imagePath: String?) {
    imagePath?.let { path ->
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(path)
        val contentUri: Uri = Uri.fromFile(f)
        intent.data = contentUri
        context.applicationContext.sendBroadcast(intent)
    }
}

