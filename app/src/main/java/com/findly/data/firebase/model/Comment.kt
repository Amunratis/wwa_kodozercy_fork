package com.findly.data.firebase.model

import java.io.Serializable

/**
 * Created by Wojdor on 21.04.2018.
 */
class Comment : Serializable {
    var userName: String = ""
    var content: String = ""
    var idAuction: String = ""
    var isAccepted: Boolean = false
}