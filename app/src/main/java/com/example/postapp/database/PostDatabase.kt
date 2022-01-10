package com.example.postapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.postapp.dao.PostDao
import com.example.postapp.models.Post

@Database(entities = arrayOf(Post::class), version = 2, exportSchema = false)
abstract class PostDatabase : RoomDatabase() {

    abstract fun getPostDao(): PostDao

    companion object{

        @Volatile
        private var INSTANCE : PostDatabase? = null

        fun getDataBase (context: Context) : PostDatabase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostDatabase::class.java,
                    "post_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}