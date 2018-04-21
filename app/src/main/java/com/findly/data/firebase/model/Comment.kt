package com.findly.data.firebase.model

/**
 * Created by Wojdor on 21.04.2018.
 */
data class Comment(var userName: String,
                   var content: String,
                   var idAuction: String,
                   var isAccepted: Boolean)