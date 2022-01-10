package com.example.postapp.repository

import androidx.lifecycle.LiveData
import com.example.postapp.models.Post

interface PostRepository {

    suspend fun addPost(post : Post)

    suspend fun deletePost(post : Post)

    fun getAllPosts() : LiveData<List<Post>>

    fun searchDatabase(searchQuery: String): LiveData<List<Post>>
}