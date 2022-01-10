package com.example.postapp.repository

import androidx.lifecycle.LiveData
import com.example.postapp.dao.PostDao
import com.example.postapp.models.Post

class DefaultPostRepository(private val postDao: PostDao) : PostRepository {

    private val allPosts1 : LiveData<List<Post>> = postDao.getAllPosts()

    override suspend fun addPost(post: Post) {
        postDao.insert(post)
    }

    override suspend fun deletePost(post: Post) {
        postDao.delete(post)
    }

    override fun searchDatabase(searchQuery: String): LiveData<List<Post>> {
        return postDao.searchDatabase(searchQuery)
    }

    override fun getAllPosts(): LiveData<List<Post>>  = allPosts1
}