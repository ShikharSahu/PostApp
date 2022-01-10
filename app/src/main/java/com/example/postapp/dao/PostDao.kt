package com.example.postapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.postapp.models.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (post : Post)

    @Delete
    suspend fun delete (post : Post)

    @Query("Select * from post_table order by id ASC")
    fun getAllPosts() : LiveData<List<Post>>

    @Query("SELECT * FROM post_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Post>>
}