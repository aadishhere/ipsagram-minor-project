package com.aadish.ipsagram.ui.viewModels

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aadish.ipsagram.data.model.Event
import com.aadish.ipsagram.data.model.User
import com.aadish.ipsagram.data.savedPostIDs
import com.aadish.ipsagram.data.service.EventService
import com.aadish.ipsagram.data.service.UserService
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel@Inject constructor(
    private val eventService: EventService,
    private val userService: UserService
): ViewModel() {
    var user = User()

    /* Save event for display on event detail*/
    var event by mutableStateOf<Event?>(null)
            private set

    var eventList by mutableStateOf<List<Event>>(listOf<Event>())
        private set

    private var savedEventIds = mutableSetOf<String>()

    init {
        viewModelScope.launch() {
            eventService.addListener(Timestamp.now(), {}) { newEventList ->
                eventList = newEventList
            }
            userService.user.collect{
                user = it ?: User()
            }
//            events::setValue
        }
    }

    fun fetchEvent(eventId: String): Flow<Event?> {
        return eventService.getEvent(eventId)
    }

    fun addEvent(newEvent: Event) {
        event = newEvent
    }

    suspend fun updateSaveEventIds(newFieldValue: FieldValue) {
        val updateMap = hashMapOf(
            "savedEventIds" to newFieldValue,
        )
        userService.updateUserField(updateMap)
    }

    fun fetchEvents(date: Timestamp = Timestamp.now()): MutableList<Event>? {
//        Log.d(ContentValues.TAG, "Paco: today is $date")
//        viewModelScope.launch {
//            val events = service.getEvents(date)
//            Log.d(ContentValues.TAG, "Paco: events = $events")
//
//        }
//        return runBlocking{
//            service.getEvents(date)
//        }
        return eventService.getEvents(date)
    }

    fun fetchSavedEvents(date: Timestamp = Timestamp.now()): MutableList<Event>? {
//        Log.d(ContentValues.TAG, "Paco: today is $date")
//        viewModelScope.launch {
//            val events = service.getEvents(date)
//            Log.d(ContentValues.TAG, "Paco: events = $events")
//
//        }
//        return runBlocking{
//            service.getEvents(date)
//        }
        return eventService.getEvents(date)
    }

    fun onSaveClicked(context: Context, eventId: String, isSaved: Boolean) {
        Log.d(ContentValues.TAG, "Paco: onSave Event =  $eventId")
        viewModelScope.launch {
            if (isSaved) {
                savedEventIds.remove(eventId)
                updateSaveEventIds(FieldValue.arrayRemove(eventId))
            } else {
                savedEventIds.add(eventId)
                updateSaveEventIds(FieldValue.arrayUnion(eventId))
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun fetchAndStoreSavedEventIds(context: Context) {
        val sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        Log.d(ContentValues.TAG, "Paco: fetchAndStoreSavedPostIds")
        viewModelScope.launch() {
            context.savedPostIDs.edit { it.clear() }
            userService.user.collect { user ->
                Log.d(ContentValues.TAG, "Paco: collect user")
                val savedPostsId = user?.savedEventIds
                if (savedPostsId != null) {
                    for (postId in savedPostsId) {
                        Log.d(ContentValues.TAG, "Paco: save $postId")
                        savedEventIds.add(postId)
//                        storeSavedPostId(context, postId, true)
                    }
                }
                Log.d(ContentValues.TAG, "Paco: fetch and store Events =  ${savedEventIds.toList()}")
            }
//            sharedPref.edit()
//                .putStringSet("savedPostIds", savedPOstIds)
//                .apply()
        }
    }
}