package com.findly.data.firebase

import android.graphics.Bitmap
import com.findly.data.firebase.model.Post
import com.google.android.gms.tasks.OnCompleteListener
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

    fun uploadImage(bitmap: Bitmap, completeListener: OnCompleteListener<UploadTask.TaskSnapshot>) {
        with(ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            FirebaseStorage.getInstance("gs://hackaton-4a09c.appspot.com")
                    .reference.child("images/${bitmap.hashCode()}.jpg")
                    .putBytes(this.toByteArray())
                    .addOnCompleteListener {
                        completeListener
                    }
                    .addOnFailureListener {
                        it.printStackTrace()
                    }
        }


    }

    private fun getPosts(dataSnapshot: DataSnapshot?): MutableList<Post> {
        val posts = mutableListOf<Post>()
        dataSnapshot?.children?.forEach {
            val post = it.getValue(Post::class.java)
            post?.let { posts.add(it) }
        }
        return posts
    }
}