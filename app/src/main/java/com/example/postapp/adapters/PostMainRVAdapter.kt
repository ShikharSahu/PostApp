package com.example.postapp.adapters

import android.app.Application
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
import com.example.postapp.repository.InternalStorageRepository
import com.example.postapp.utils.TimeUtils
import java.io.File
import java.lang.Exception

class PostMainRVAdapter(private val context: Context) : RecyclerView.Adapter<PostMainRVAdapter.PostViewHolder> (){

    private val allPostsList = ArrayList<Post>()
    private val internalStorageRepository : InternalStorageRepository
        = InternalStorageRepository(context as Application)


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
                val currentImageFile = internalStorageRepository.
                    loadPhotoFromInternalStorage(currentPost.imageFile)
                currentHolderBinding.postImage.setImageBitmap(currentImageFile)
            }catch (e : Exception){}
        }
    }

    fun updateList( posts: List <Post>) {
        allPostsList.clear()
        allPostsList.addAll(posts)
        // TODO use diffutil
        notifyDataSetChanged()
    }
}