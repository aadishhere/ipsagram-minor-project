package com.aadish.ipsagram.data.service

import com.aadish.ipsagram.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    val user: Flow<User?>

    suspend fun updateUserField(updateMap: Map<String, Any>)

    fun addMyPostId(postId: String)
}