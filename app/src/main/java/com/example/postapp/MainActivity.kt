package com.example.postapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.postapp.adapters.PostMainRVAdapter
import com.example.postapp.databinding.ActivityMainBinding
import com.example.postapp.viewmodels.PostMainViewModel
import android.widget.Toast

import android.app.ListActivity

import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.ItemTouchHelper


class MainActivity : AppCompatActivity(),  SearchView.OnQueryTextListener {

    private lateinit var viewModel: PostMainViewModel
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : PostMainRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.postMainRv.layoutManager = LinearLayoutManager(this)
        adapter = PostMainRVAdapter()
        binding.postMainRv.adapter = adapter


        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PostMainViewModel::class.java)

        viewModel.getAllPost().observe(this, Observer {it1->
            it1?.let {
                adapter.updateList(it)
            }
        })

        binding.addPostButton.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)
        }
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                adapter.updateList(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return true
    }


//    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
//        ItemTouchHelper.SimpleCallback(
//            0,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
//        ) {
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//            Toast.makeText(this@ListActivity, "on Move", Toast.LENGTH_SHORT).show()
//            return false
//        }
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//            Toast.makeText(this@ListActivity, "on Swiped ", Toast.LENGTH_SHORT).show()
//            //Remove swiped item from list and notify the RecyclerView
//            val position = viewHolder.adapterPosition
//            arrayList.remove(position)
//            adapter.notifyDataSetChanged()
//        }
//    }

}