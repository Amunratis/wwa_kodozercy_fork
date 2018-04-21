package com.findly.data.firebase.model

/**
 * Created by Wojdor on 21.04.2018.
 */
class Post {
    var imageUrl: String = ""
    var comments: MutableList<Comment> = mutableListOf()
    var userName: String = ""
    var description: String = ""
}