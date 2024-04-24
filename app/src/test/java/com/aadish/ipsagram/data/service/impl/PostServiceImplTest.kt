package com.aadish.ipsagram.data.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

//@Config(application = MyApplication::class)
class PostServiceImplTest {
//    val service = PostServiceImpl(Firebase.firestore)
    val TEST_POST_ID = "ox1rzs8XnOVcxhMJm7zr"

    private lateinit var service: PostServiceImpl

    @Mock
    private lateinit var mockFirestore: FirebaseFirestore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        service = PostServiceImpl(mockFirestore)
    }


    @Test
    fun getPost() {
//        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext())
        println(mockFirestore.firestoreSettings)
        val post = service.getPost(TEST_POST_ID)
        runBlocking {
            post.collect {
                assertNotNull(it)
                assertEquals(it!!.id, TEST_POST_ID)
            }
        }
    }
}