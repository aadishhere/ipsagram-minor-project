package com.aadish.ipsagram.data.service.impl

import com.aadish.ipsagram.data.model.User
import com.aadish.ipsagram.data.service.AuthService
import com.aadish.ipsagram.data.service.UserService
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AuthService
): UserService {
    override val user: Flow<User?>
        get() = firestore.collection(USER_COLLECTION)
            .document(auth.currentUser?.uid ?: "ss5SpophLA7MXDB19CWo").dataObjects<User>()

    override suspend fun updateUserField(updateMap: Map<String, Any>) {
        firestore.collection(USER_COLLECTION)
            .document(auth.currentUser?.uid!!).update(updateMap).await()
    }

    override fun addMyPostId(postId: String) {
        firestore.collection(USER_COLLECTION).document(auth.currentUser?.uid!!)
            .update(mapOf(
                "myPostIds" to FieldValue.arrayUnion(postId)
            ))
    }


    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val USER_COLLECTION = "posts"
    }

}