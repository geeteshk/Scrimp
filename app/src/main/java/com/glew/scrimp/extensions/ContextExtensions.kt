package com.glew.scrimp.extensions

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.glew.scrimp.BuildConfig
import java.io.File

fun Context.getTakePictureUri(): Uri {
    val filesDir = this.filesDir
    val file = File.createTempFile("receipt_", ".png", filesDir).apply { createNewFile() }
    return FileProvider.getUriForFile(this.applicationContext, "${BuildConfig.APPLICATION_ID}.provider", file)
}