package com.aadish.ipsagram.data.service

import com.aadish.ipsagram.data.model.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    val currentUser: FirebaseUser?
//    suspend fun login(email: String, password: String): Resource<FirebaseUser>
//    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser>
//    fun logout()

    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String, username: String): Response<Boolean>
    suspend fun sendEmailVerification(): Response<Boolean>
    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): Response<Boolean>
    suspend fun reloadFirebaseUser(): Response<Boolean>
    suspend fun sendPasswordResetEmail(email: String): Response<Boolean>
    fun signOut()
    suspend fun revokeAccess(): Response<Boolean>
    fun getAuthState(viewModelScope: CoroutineScope): StateFlow<Boolean>


//    fun firebaseCreateUser(email: String, username: String): Response<Boolean>
}