package com.example.postapp.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.postapp.database.PostDatabase
import com.example.postapp.models.Post
import com.example.postapp.repository.DefaultPostRepository
import com.example.postapp.repository.InternalStorageRepository
import com.example.postapp.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddPostViewModel ( application: Application) : AndroidViewModel(application) {


    private val repository : PostRepository
    private val internalStorageRepository : InternalStorageRepository
    var imageFileName : MutableLiveData<String>

    init {
        val dao = PostDatabase.getDataBase(application).getPostDao()
        repository = DefaultPostRepository(dao)
        imageFileName = MutableLiveData("")
        internalStorageRepository = InternalStorageRepository(application)
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

    fun loadPhotoFromInternalStorage(filename: String) : Bitmap?
        = internalStorageRepository.loadPhotoFromInternalStorage(filename)

    fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean
        = internalStorageRepository.savePhotoToInternalStorage(filename,bmp)
}