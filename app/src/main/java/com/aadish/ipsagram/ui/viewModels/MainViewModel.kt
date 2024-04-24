package com.aadish.ipsagram.ui.viewModels

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aadish.ipsagram.data.Preferences
import com.aadish.ipsagram.data.model.User
import com.aadish.ipsagram.data.service.AuthService
import com.aadish.ipsagram.data.userPreferences
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: AuthService
): ViewModel() {
    val isEmailVerified get() = service.currentUser?.isEmailVerified ?: false
    val userID get() = service.currentUser?.uid.toString()
    val user: Flow<User?>
        get() = Firebase.firestore.collection("/users").document(userID).dataObjects<User>()

    init {
        getAuthState()
    }

    fun getAuthState() = service.getAuthState(viewModelScope)


//    fun storeUserPreference(context: Context) {
//        viewModelScope.launch {
//            user.collect() {user ->
//                context.userPreferences.edit { preferences ->
//                    preferences[Preferences.USER_ID_KEY] = userID
//                    preferences[Preferences.USER_NAME_KEY] = user!!.name        // add field to user (name)
//                    Log.d(TAG,"Paco: user Name = ${user!!.name}")
//                }
//
//            }
//        }
//    }


}