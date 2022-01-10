package com.example.postapp.viewmodels

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postapp.database.PostDatabase
import com.example.postapp.models.Post
import com.example.postapp.repository.DefaultPostRepository
import com.example.postapp.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun deletePost(position : Int){
        viewModelScope.launch(Dispatchers.IO) {
            allPosts.value!!.get(position)?.let { repository.deletePost(it)
                Log.d(TAG, "deletePost: post deleted $position")}
            Log.d(TAG, "deletePost: not post deleted $position")
        }
    }

//    fun sharePost(position: Int){
//        allPosts.value?.get(position)?.let{
//            if ()
//        }
//    }



    fun searchDatabase(searchQuery: String): LiveData<List<Post>> {
        return repository.searchDatabase(searchQuery)
    }
}