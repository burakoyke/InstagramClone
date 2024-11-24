package com.burakoyke.instagramcloneapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakoyke.instagramcloneapp.adapter.FeedRecyclerAdapter
import com.burakoyke.instagramcloneapp.databinding.ActivityFeedBinding
import com.burakoyke.instagramcloneapp.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore : FirebaseFirestore
    val postArrayList : ArrayList<Post> = ArrayList()
    var adapter : FeedRecyclerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Instagram Clone"
        setSupportActionBar(binding.toolbar)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        getData()

        binding.rv.layoutManager = LinearLayoutManager(this)
        adapter = FeedRecyclerAdapter(postArrayList)
        binding.rv.adapter = adapter


    }

    fun getData() {
        firestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                        postArrayList.clear()
                        val documents = value.documents
                        documents.forEach {
                            val comment = it.get("comment") as String
                            val userEmail = it.get("userEmail") as String
                            val downloadUrl = it.get("downloadUrl") as String

                            val post = Post(userEmail,comment, downloadUrl)
                            postArrayList.add(post)
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.insta_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_post) {
            val intent = Intent (this@FeedActivity, UploadActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.sign_out) {
            auth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}