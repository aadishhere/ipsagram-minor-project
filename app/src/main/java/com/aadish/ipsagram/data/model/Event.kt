package com.aadish.ipsagram.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Event(
//    val writer: User = User(),
    @DocumentId val id: String = "",
    val title: String = "Institute of Engineering & Science, IPS Academy",
    val category: String = "Seminar",
    val tags: List<String> = listOf("environment", "education"),
    val description: String = "N/A",
    val language: String = "English",
    val location: String = "Rajendra Nagar, Indore, Madhya Pradesh, 452002",
    val eventDate: Timestamp = Timestamp.now(),
    val eventTime: String = "N/A",
    val uploadTime: Timestamp = Timestamp.now(),
    val isFormal: Boolean = false,
    var isExpired: Boolean = false,
    val registerLink: String = "",
    val imageURL: String = ""


)