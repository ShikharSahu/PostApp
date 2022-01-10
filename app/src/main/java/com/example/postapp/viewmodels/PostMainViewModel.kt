package com.example.postapp.viewmodels

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.postapp.database.PostDatabase
import com.example.postapp.models.Post
import com.example.postapp.repository.DefaultPostRepository
import com.example.postapp.repository.InternalStorageRepository
import com.example.postapp.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import android.content.Intent

import android.net.Uri
import androidx.core.content.FileProvider


class PostMainViewModel( application: Application) : AndroidViewModel(application) {
    private var allPosts : LiveData<List<Post>>
    private val repository : PostRepository
    private val internalStorageRepository : InternalStorageRepository


    init {
        val dao = PostDatabase.getDataBase(application).getPostDao()
        repository = DefaultPostRepository(dao)
        allPosts = repository.getAllPosts()
        internalStorageRepository = InternalStorageRepository(application)
    }

    fun getAllPost() : LiveData<List<Post>> {
        return allPosts
    }

    fun deletePost(position : Int){
        viewModelScope.launch(Dispatchers.IO) {
            allPosts.value!!.get(position)?.let { repository.deletePost(it)
                Log.d(TAG, "deletePost: post deleted $position")}
        }
    }

    fun sharePost(position: Int) : Intent{
        allPosts.value!!.get(position)?.let{
//            if (it.imageFile.isNotEmpty()){
//                val imgFile = File(getApplication<Application>().applicationContext.filesDir,
//                    it.imageFile)
//
//                val context = getApplication<Application>().applicationContext
//                val file = File(context.cacheDir, it.imageFile);
//
//                val contentUri = FileProvider.getUriForFile(context, "com.example.postapp", file);
//                val shareIntent = Intent(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
//                shareIntent.type = "text/plain";
//                shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
//
//
//                return Intent.createChooser(shareIntent, null)
//            }
//            else {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, it.title)
                    type = "text/plain"
                }

                return Intent.createChooser(sendIntent, null)
//            }
        }
    }


    fun searchDatabase(searchQuery: String): LiveData<List<Post>> {
        return repository.searchDatabase(searchQuery)
    }
}