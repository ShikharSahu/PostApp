package com.example.postapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.postapp.database.PostDatabase
import com.example.postapp.models.Post
import com.example.postapp.repository.DefaultPostRepository
import com.example.postapp.repository.PostRepository

class PostMainViewModel( application: Application) : AndroidViewModel(application) {
    private var allPosts : LiveData<List<Post>>
    private var repository : PostRepository

    init {
        val dao = PostDatabase.getDataBase(application).getPostDao()
        repository = DefaultPostRepository(dao)
        allPosts = repository.getAllPosts()
    }

    fun getAllPost() : LiveData<List<Post>> {
        return allPosts
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Post>> {
        return repository.searchDatabase(searchQuery)
    }
}