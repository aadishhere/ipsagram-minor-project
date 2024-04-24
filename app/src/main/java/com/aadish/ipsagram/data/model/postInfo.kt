package com.aadish.ipsagram.data.model

import com.google.firebase.Timestamp


data class postInfo(
    val category: String,
    val content:String,
    val time:Timestamp,
    val title:String,
    val link:String
)
