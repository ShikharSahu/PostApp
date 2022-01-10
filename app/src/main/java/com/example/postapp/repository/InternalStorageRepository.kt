package com.example.postapp.repository

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException

class InternalStorageRepository(private val application: Application) {

    fun loadPhotoFromInternalStorage(filename: String) : Bitmap? {

        val imgFile = File(application.filesDir, filename)
        if(imgFile.exists()){
            val imgByteArray = imgFile.readBytes()
            Log.d(ContentValues.TAG, "loadPhotoFromInternalStorage: loading from internal $filename" )
            return BitmapFactory.decodeByteArray(imgByteArray,0,imgByteArray.size)
        }
        else{
            Log.d(ContentValues.TAG, "loadPhotoFromInternalStorage: doesn't exist")
        }
        return null
    }

    fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
            application.openFileOutput(filename, AppCompatActivity.MODE_PRIVATE).use { stream ->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
                else{
                    Log.d(ContentValues.TAG, "savePhotoToInternalStorage: ")
                }
            }
            Log.d(ContentValues.TAG, "savePhotoToInternalStorage: saved internally ")
            true
        } catch(e: IOException) {
            e.printStackTrace()
            false
        }
    }


}