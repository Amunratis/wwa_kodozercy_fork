package com.findly.data.firebase

import com.findly.data.firebase.model.Post
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Wojdor on 21.04.2018.
 */
object Database {
    private val database = FirebaseDatabase.getInstance().reference

    fun addPost(post: Post) {
        val key = database.child("posts").push().key
        database.child("posts").child(key).setValue(post)
    }
}