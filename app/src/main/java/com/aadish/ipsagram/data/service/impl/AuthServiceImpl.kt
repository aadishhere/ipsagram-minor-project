package com.aadish.ipsagram.data.service.impl

import android.util.Log
import com.aadish.ipsagram.data.service.AuthService
import com.aadish.ipsagram.data.model.Response
//import com.example.finalproject.data.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthService {
    override val currentUser get() = auth.currentUser

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String, username: String
    ) = try {
        auth.createUserWithEmailAndPassword(email, password).await()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
        val userId = auth.currentUser?.uid!!
//        val user = User(name = username, uid = userId)
        val userRef = FirebaseFirestore.getInstance().collection("users")
//        userRef.set(user)
        userRef.add(
            mapOf(
                "email" to email,
                "name" to username,
                "password" to password
            )
        ).addOnSuccessListener { documentReference ->
            // Data added successfully, handle logic here
            Log.d("Firestore", "Data added with ID: ${documentReference.id}")
        }
//                }
//            }
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun sendEmailVerification() = try {
        auth.currentUser?.sendEmailVerification()?.await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String
    ) = try {
        auth.signInWithEmailAndPassword(email, password).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun reloadFirebaseUser() = try {
        auth.currentUser?.reload()?.await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun sendPasswordResetEmail(email: String) = try {
        auth.sendPasswordResetEmail(email).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override fun signOut() = auth.signOut()

    override suspend fun revokeAccess() = try {
        // delete account from firebase
        val userId = auth.currentUser?.uid!!
        val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)
        userRef.delete()

        auth.currentUser?.delete()?.await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)



//    override fun firebaseCreateUser(email: String, username: String): Response<Boolean> = try{
//
//    }

}