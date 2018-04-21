package com.findly.data.firebase.model

/**
 * Created by Wojdor on 21.04.2018.
 */
data class Post(var imageUrl: String,
                var comments: MutableList<Comment>,
                var userName: String)