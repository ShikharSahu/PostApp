package com.example.postapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.postapp.databinding.ActivityAddPostBinding
import androidx.lifecycle.ViewModelProvider
import com.example.postapp.viewmodels.AddPostViewModel
import java.net.URI


class AddPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    private lateinit var viewModel: AddPostViewModel
    private lateinit var uri : URI;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(AddPostViewModel::class.java)

        binding.btnAddPostSubmit.setOnClickListener {
            viewModel.addPost(
                binding.ETaddPostTitle.toString(),
                binding.ETaddPostDescription.toString(),
                uri
            )
        }


    }



    fun openImageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startForResult.launch(i)
    }



    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val selectedImageUri = intent?.data

            if (null != selectedImageUri) {
                // update the preview image in the layout
                binding.ivAddPostSelectedPicture.setImageURI(selectedImageUri);
            }
        }
    }
}