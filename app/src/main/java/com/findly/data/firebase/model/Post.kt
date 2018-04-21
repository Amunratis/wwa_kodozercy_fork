package com.findly.data.firebase.model

/**
 * Created by Wojdor on 21.04.2018.
 */
class Post {
    var key: String = ""
    var imageUrl: String = ""
    var comments: MutableList<Comment> = mutableListOf()
    var userName: String = ""
}