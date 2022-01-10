package com.example.postapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
class Post (val title : String,
            val description : String,
            val imageFile : String,
            val timeSavedAt : Long){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}