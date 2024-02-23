package com.skyblue.recyclerview_swip_to_delete

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.skyblue.recyclerview_swip_to_delete.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var postAdapter: PostAdapter
    lateinit var postList: ArrayList<Post>
    var context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postList = ArrayList();

        postAdapter = PostAdapter(context, postList)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = postAdapter

        postList.add(Post("Android Development", R.drawable.android))
        postList.add(Post("C++ Development", R.drawable.cpp1))
        postList.add(Post("Java Development", R.drawable.java))
        postList.add(Post("Json Development", R.drawable.json))
        postList.add(Post("JavaScript Development", R.drawable.js))
        postAdapter.notifyDataSetChanged()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedPost: Post =
                    postList.get(viewHolder.adapterPosition)

                val position = viewHolder.adapterPosition

                postList.removeAt(viewHolder.adapterPosition)

                postAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(binding.recyclerView, "Deleted " + deletedPost.name, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        {
                            postList.add(position, deletedPost)
                            postAdapter.notifyItemInserted(position)
                        }).show()
            }
        }).attachToRecyclerView(binding.recyclerView)
    }
}