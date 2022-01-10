package com.example.postapp.adapters

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.postapp.databinding.PostRvRowItemBinding
import com.example.postapp.models.Post
import com.example.postapp.utils.TimeUtils
import java.io.File
import java.lang.Exception

class PostMainRVAdapter(val context: Context) : RecyclerView.Adapter<PostMainRVAdapter.PostViewHolder> (){

    private val allPostsList = ArrayList<Post>()

    inner class PostViewHolder(val binding : PostRvRowItemBinding) :
        RecyclerView.ViewHolder(binding.root){}

    override fun getItemCount(): Int {
        return allPostsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostRvRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val postViewHolder = PostViewHolder(binding)
        return postViewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentHolderBinding = holder.binding
        val currentPost = allPostsList[position]
        currentHolderBinding.postTitle.text = currentPost.title
        currentHolderBinding.postDescription.text = currentPost.description
        val timeAsPeriod = TimeUtils.getTimeAgo(currentPost.timeSavedAt);
        currentHolderBinding.postTime.text = "saved $timeAsPeriod"

        if(currentPost.imageFile.isNullOrBlank()){
            currentHolderBinding.postImage.visibility = View.GONE
        }
        else{
            currentHolderBinding.postImage.visibility = View.VISIBLE
            try{
                val currentImageFile = loadPhotoFromInternalStorage(currentPost.imageFile)
                currentHolderBinding.postImage.setImageBitmap(currentImageFile)
            }catch (e : Exception){}
        }
//        currentHolderBinding.imageView.
    }
    fun loadPhotoFromInternalStorage(filename: String) : Bitmap? {
//        val imgFiles = filesDir.listFiles()
//        for(a in imgFiles){
//            Log.d(TAG, "loadPhotoFromInternalStorage: ${a.absolutePath}")
//        }
//        return null
//        Log.d(TAG, "loadPhotoFromInternalStorage: ${imgFile.canonicalPath}")
//
        val imgFile = File(context.filesDir, filename)
        if(imgFile.exists()){
            val imgByteArray = imgFile.readBytes()
            Log.d(ContentValues.TAG, "loadPhotoFromInternalStorage: loading from internal $filename" )
            return BitmapFactory.decodeByteArray(imgByteArray,0,imgByteArray.size)
        }
        else{
            Log.d(ContentValues.TAG, "loadPhotoFromInternalStorage: doesn't exist")
        }
        return null
    }



    fun updateList( posts: List <Post>) {
        allPostsList.clear()
        allPostsList.addAll(posts)
        // TODO use diffutil
        notifyDataSetChanged()
    }
}