package com.aadish.ipsagram.data.service.impl

import android.content.ContentValues
import android.util.Log
import com.aadish.ipsagram.data.model.Event
import com.aadish.ipsagram.data.service.EventService
//import com.example.finalproject.data.utils.await
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
//    private val auth: AuthService,
): EventService {
    val events = mutableMapOf<String, Event>()

    override fun addListener(
        date: Timestamp,
        onError: (Throwable) -> Unit,
        eventsSateSetter: (List<Event>) -> Unit
    ) {
        val query = firestore.collection(EVENT_TEST_COLLECTION)
                        .whereEqualTo(EVENT_IS_EXPIRED, false)
//                        .orderBy("eventTime", Query.Direction.DESCENDING)

        query.addSnapshotListener { value, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {
                Log.d(ContentValues.TAG, "Paco: event type =  ${it.type}")
                val wasEventAdded = (it.type == DocumentChange.Type.ADDED)
                val event = it.document.toObject(Event::class.java).copy()

                if (wasEventAdded) {
                    Log.d(ContentValues.TAG, "Paco: Add event: $event")
                    events[event.id] = event
                } else {
                    Log.d(ContentValues.TAG, "Paco: removed event: $event")
                    events.remove(event.id)
                    // remove ld events.
                }
            }
            eventsSateSetter(events.values.toList())

        }

    }

    override fun getEvents(date: Timestamp): MutableList<Event>? {
//        return firestore.collection(EVENT_COLLECTION)
//            .whereEqualTo(EVENT_IS_EXPIRED, false).get().await()?.toObjects(Event::class.java)
//
        return events.values.toMutableList()
    }

    override fun getEvent(eventId: String): Flow<Event?> =
        firestore.collection(EVENT_TEST_COLLECTION).document(eventId).dataObjects<Event>()

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val USER_COLLECTION = "users"
        private const val EVENT_COLLECTION = "events"
        private const val EVENT_TEST_COLLECTION = "event_upload_test"
        private const val EVENT_IS_EXPIRED = "isExpired"
    }
}