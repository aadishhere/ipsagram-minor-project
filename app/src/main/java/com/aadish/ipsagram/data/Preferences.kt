/*
* This file contains all the code needed to get data from preference datastore
* (Not using SharedPreference since Google suggestion)
*
*/
package com.aadish.ipsagram.data

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
//import com.example.finalproject.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.userPreferences by preferencesDataStore(name = "userPreferences")
val Context.savedPostIDs by preferencesDataStore(name = "savedPostIDs")

object Preferences {
    val USER_ID_KEY = stringPreferencesKey("userID")
    val USER_NAME_KEY = stringPreferencesKey("userName")

    fun getUserId(context: Context, callback: (String?) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val userID = context.userPreferences.data.map { preferences ->
                preferences[USER_ID_KEY]
            }.first()
            callback(userID)
        }
    }

    fun getUserName(context: Context, callback: (String?) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val userName = context.userPreferences.data.map { preferences ->
                preferences[USER_NAME_KEY]
            }.first()
            callback(userName)
        }
    }
}
//
//
//suspend fun storeSavedPostId(
//    context: Context,
//    postId: String,
//    isSaved: Boolean = true
//){
//    val key = booleanPreferencesKey(postId)
//    context.savedPostIDs.edit { preferences ->
//        preferences[key] = isSaved
//    }
//}
//
//suspend fun removeSavedPostId(
//    context: Context,
//    postId: String
//){
//    val key = booleanPreferencesKey(postId)
//    context.savedPostIDs.edit { preferences ->
//        preferences.remove(key)
//    }
//}
//
////fun checkSavedPost(
////    context: Context,
////    postId: String
////): Flow<Boolean> {
////    val key = booleanPreferencesKey(postId)
////    Log.d(TAG, "Paco: checkSavedPost $postId")
////    return context.savedPostIDs.data.map { preferences ->
////        preferences.contains(key)
////    }
////}
//
//
//fun getSavedPost(
//    context: Context,
//    postId: String
//): Flow<Preferences> {
//    val key = booleanPreferencesKey(postId)
//    Log.d(TAG, "Paco: checkSavedPost $postId")
//    return context.savedPostIDs.data
//}
