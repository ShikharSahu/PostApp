package com.example.postapp.viewmodels

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.postapp.database.PostDatabase
import com.example.postapp.models.Post
import com.example.postapp.repository.DefaultPostRepository
import com.example.postapp.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException


class AddPostViewModel ( application: Application) : AndroidViewModel(application) {


    private var repository : PostRepository
//    private lateinit var imageFileName : String
    lateinit var imageFileName : MutableLiveData<String>

    init {
        val dao = PostDatabase.getDataBase(application).getPostDao()
        repository = DefaultPostRepository(dao)
        imageFileName = MutableLiveData("")
//        imageFileName.value= ""
    }

    fun addPost(
        title: String,
        description: String
    ){
        val newPost = Post(title,description, imageFileName.value.toString(), System.currentTimeMillis())

        viewModelScope.launch(Dispatchers.IO) {
            repository.addPost(newPost)
        }
    }

    fun updateImgFile(fileName: String){
        imageFileName.value = fileName
    }

    fun loadPhotoFromInternalStorage(filename: String) : Bitmap? {
//        val imgFiles = filesDir.listFiles()
//        for(a in imgFiles){
//            Log.d(TAG, "loadPhotoFromInternalStorage: ${a.absolutePath}")
//        }
//        return null
//        Log.d(TAG, "loadPhotoFromInternalStorage: ${imgFile.canonicalPath}")
//
        val imgFile = File(getApplication<Application>().baseContext.filesDir, filename)
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
            getApplication<Application>().baseContext.openFileOutput(filename, AppCompatActivity.MODE_PRIVATE).use { stream ->
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