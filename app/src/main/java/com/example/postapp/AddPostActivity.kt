package com.example.postapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.postapp.databinding.ActivityAddPostBinding
import com.example.postapp.utils.InternalStorageUtils
import com.example.postapp.viewmodels.AddPostViewModel


class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding
    private lateinit var viewModel: AddPostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(AddPostViewModel::class.java)

        binding.btnChooseFromGallery.setOnClickListener{
            openImageChooser()
        }
        
        binding.btnAddPostchooseFromCam.setOnClickListener{
            takePhoto.launch()
        }

        binding.btnAddPostSubmit.setOnClickListener {
            val title = binding.ETaddPostTitle.text.toString()
            val description = binding.ETaddPostDescription.text.toString()
            if(title.isNotEmpty() and description.isNotEmpty()) {
                viewModel.addPost(
                    title,
                    description
                )
                finish()
            }
            else{
                Toast.makeText(this,"Title/Description empty",Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.imageFileName.observe(this,{
            if(it.isNullOrEmpty()) {
                binding.ivAddPostSelectedPicture.visibility = View.GONE
            }
            else{
                val imgBitmap = viewModel.loadPhotoFromInternalStorage(it)
                if(imgBitmap != null){
                    binding.ivAddPostSelectedPicture.visibility = View.VISIBLE
                    binding.ivAddPostSelectedPicture.setImageBitmap(imgBitmap)
                }
            }
        }
        )
    }

    val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview())  {
        val filename = InternalStorageUtils.getImageFileName()
        val isSavedSuccessfully = it?.let { it1 ->
            viewModel.savePhotoToInternalStorage(filename,
                it1
            )
        }
        viewModel.updateImgFile(filename)
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
                binding.ivAddPostSelectedPicture.setImageURI(selectedImageUri);
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(baseContext.contentResolver, selectedImageUri))
                } else {
                    MediaStore.Images.Media.getBitmap(baseContext.contentResolver, selectedImageUri)
                }
                val filename = InternalStorageUtils.getImageFileName()

                val isSaved = viewModel.savePhotoToInternalStorage(filename,bitmap)
                if(isSaved){
                    viewModel.updateImgFile(filename)
                }
            }
        }
        
    }
}