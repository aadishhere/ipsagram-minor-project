package com.aadish.ipsagram.data.model

import com.google.firebase.Timestamp

data class PostInfoState(
    val title:String="",
    val category: String="",
   val content:String="",
    val time:Timestamp=Timestamp.now(),
    val link:String=""
)
