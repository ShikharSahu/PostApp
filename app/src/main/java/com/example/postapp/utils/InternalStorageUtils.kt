package com.example.postapp.utils

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.File

class InternalStorageUtils() {
    companion object{
        fun getImageFileName() : String = "imgSaved${System.currentTimeMillis()}.jpg"

    }
}