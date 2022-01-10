package com.example.postapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postapp.databinding.PostRvRowItemBinding
import com.example.postapp.models.Post
import com.example.postapp.utils.TimeUtils

class PostMainRVAdapter : RecyclerView.Adapter<PostMainRVAdapter.PostViewHolder> (){

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
        }
//        currentHolderBinding.imageView.
    }

    fun updateList( notes : List <Post>) {
        allPostsList.clear()
        allPostsList.addAll(notes)
        // TODO use diffutil
        notifyDataSetChanged()
    }
}