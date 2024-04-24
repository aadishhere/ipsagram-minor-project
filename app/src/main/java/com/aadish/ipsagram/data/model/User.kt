package com.aadish.ipsagram.data.model

data class User (
    val name: String = "User",
    val school: String = "IES IPS Academy",
    val uid: String = "0",
    val email: String = "",
    var savedEventIds: MutableList<String> = mutableListOf<String>(),
    var savedPostIds: MutableList<String> = mutableListOf<String>(),
    var myPostIds: MutableList<String> = mutableListOf<String>()
)