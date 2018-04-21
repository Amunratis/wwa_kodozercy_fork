package com.findly.data.firebase

import android.graphics.Bitmap
import com.findly.data.firebase.model.Post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

/**
 * Created by Wojdor on 21.04.2018.
 */
object Database {
    private val database = FirebaseDatabase.getInstance().reference

    fun addPost(post: Post, onCompleteListener: OnCompleteListener<Void>) {
        val key = database.child("posts").push().key
        post.key = key
        database.child("posts").child(key).setValue(post).addOnCompleteListener(
                onCompleteListener
        )
    }


    fun getPosts(callback: (MutableList<Post>) -> Unit) {
        database.child("posts").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                callback(getPosts(dataSnapshot))
            }
        })
    }

    private fun getPosts(dataSnapshot: DataSnapshot?): MutableList<Post> {
        val posts = mutableListOf<Post>()
        dataSnapshot?.children?.forEach {
            val post = it.getValue(Post::class.java)
            post?.let { posts.add(it) }
        }
        return posts
    }

    fun getPost(key: String, callback: (Post?) -> Unit) {
        database.child("posts").child(key).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                callback(dataSnapshot?.getValue(Post::class.java))
            }
        })
    }
}