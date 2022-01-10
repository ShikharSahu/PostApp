package com.example.postapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.postapp.database.PostDatabase
import com.example.postapp.models.Post
import com.example.postapp.repository.DefaultPostRepository
import com.example.postapp.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI


class AddPostViewModel (application: Application) : AndroidViewModel(application) {

    private var repository : PostRepository
    private lateinit var viewModel: AddPostViewModel


    init {
        val dao = PostDatabase.getDataBase(application).getPostDao()
        repository = DefaultPostRepository(dao)
    }

    fun addPost(
        title: String,
        description: String,
        imageUri: URI
    ){
        val newPost = Post(title,description, imageUri.toString(), System.currentTimeMillis())

        viewModelScope.launch(Dispatchers.IO) {
            repository.addPost(newPost)
        }
    }


}