package com.aadish.ipsagram.ui.viewModels

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aadish.ipsagram.data.Preferences
import com.aadish.ipsagram.data.model.Comment
import com.aadish.ipsagram.data.model.Post
import com.aadish.ipsagram.data.model.PostInfoState
import com.aadish.ipsagram.data.model.postInfo
import com.aadish.ipsagram.data.savedPostIDs
import com.aadish.ipsagram.data.service.PostService
import com.aadish.ipsagram.data.service.UserService
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postService: PostService,
    private val userService: UserService
): ViewModel() {
    var posts = postService.posts
    var user = userService.user

    private var _postInfoState = MutableStateFlow(PostInfoState())
    var postInfoState = _postInfoState.asStateFlow()

    private var savedPostIds = mutableSetOf<String>()
    private val _postList: MutableStateFlow<List<Triple<Long, String, String>>> = MutableStateFlow(emptyList()) // Use a MutableStateFlow to hold the collected value

    var postList: StateFlow<List<postInfo>> = _postList.flatMapLatest {
        postService.getPost()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun fetchPost(): Flow<List<postInfo>> {
        return postService.getPost()
    }

    fun fetchComments(postId: String): Flow<List<Comment>> {
        return postService.getPostComment(postId)
    }
fun toUpdateState(title: String,category: String,content: String,time: Timestamp,link: String){
    _postInfoState.value = PostInfoState(title, category, content,time,link)

}

    fun updateSaveCount(postId: String, newSaveCount: Int) {
        val updateMap = hashMapOf<String, Int>(
            "saveCount" to newSaveCount,
        )
        viewModelScope.launch() {
            postService.updatePostField(postId, updateMap)
        }
        Log.d(TAG, "Paco: newSaveCount: $newSaveCount")
    }

    fun incrementView(postId: String) {
        viewModelScope.launch {
            postService.updatePostField(postId, mapOf(
                "viewCount" to FieldValue.increment(1)
            ))
        }
    }

    fun onDelete(postId: String) {
        viewModelScope.launch {
            postService.delete(postId)
        }
    }

    fun getTimeDifference(date: Date, setTimeDiffState: (String) -> Unit) {
        viewModelScope.launch {
            val difference = Date().time - date.time

            setTimeDiffState(when {
                difference < TimeUnit.MINUTES.toMillis(1) -> "just now"
                difference < TimeUnit.HOURS.toMillis(1) -> {
                    val minutes = TimeUnit.MILLISECONDS.toMinutes(difference)
                    "$minutes minutes ago"
                }
                difference < TimeUnit.DAYS.toMillis(1) -> {
                    val hours = TimeUnit.MILLISECONDS.toHours(difference)
                    "$hours hours ago"
                }
                difference < TimeUnit.DAYS.toMillis(7) -> {
                    val days = TimeUnit.MILLISECONDS.toDays(difference)
                    "$days days ago"
                }
                difference < TimeUnit.DAYS.toMillis(365) -> {
                    val weeks = TimeUnit.MILLISECONDS.toDays(difference) / 7
                    if (weeks < 4) {
                        "$weeks weeks ago"
                    } else {
                        val months = weeks / 4
                        "$months months ago"
                    }
                }
                else -> {
                    val years = TimeUnit.MILLISECONDS.toDays(difference) / 365
                    "$years years ago"
                }
            })
        }

    }


    suspend fun updateSavePostIds(newFieldValue: FieldValue) {
        val updateMap = hashMapOf(
            "savedPostIds" to newFieldValue,

        )
        userService.updateUserField(updateMap)
    }

//    fun onPostClicked(post: Post, openPostDetailScreen: (String) -> Unit) {
//        state["post_id"] = post.id
//        openPostDetailScreen(post.id)
//    }

    fun onPostCreate(context: Context, category: String, title: String, content: String,link:String) {
//        Preferences.getUserId(context) {userId ->
            val post = Post(
                category = category,
                title = title,
                content = content,
                time = Timestamp.now(),
                link = link
            )

            Log.d(TAG, "Paco: create Post = $post")
            viewModelScope.launch {
                postService.createPost(post).addOnSuccessListener { postRef ->
                    userService.addMyPostId(postRef.id)

                }
            }

    }

    fun onCommentSubmit(context: Context, post: Post, commentText: String) {
        Log.d(TAG, "Paco: submit comment for post = ${post.id}, commet = $commentText")
        Preferences.getUserId(context) {userId ->
            val report = mapOf(
                "writerId" to userId!!,
                "content" to commentText,
                "time" to Timestamp.now(),
                "sameWriter" to (userId == post.writerId),
                "deleted" to false
            )
            viewModelScope.launch {
                postService.addComment(post, report)
            }
        }
    }

    fun onReportSubmit(context: Context, postId: String, reportReason: String) {
        Log.d(TAG, "Paco: submit report:$postId")
        Preferences.getUserId(context) {userId ->
            val report = mapOf(
                "reporterId" to userId!!,
                "reportReason" to reportReason,
                "submitDateTime" to Timestamp.now()
            )
            viewModelScope.launch {
                postService.addReport(postId, report)
            }
        }
    }

    fun onSaveClicked(context: Context, post: Post, isSaved: Boolean) {
        Log.d(TAG, "Paco: onSave before ${post.id}, is saved=$isSaved")
        viewModelScope.launch {
            if (isSaved) {
                savedPostIds.remove(post.id)
                updateSavePostIds(FieldValue.arrayRemove(post.id))
                updateSaveCount(post.id, post.saveCount-1)
            } else {
                savedPostIds.add(post.id)
                updateSavePostIds(FieldValue.arrayUnion(post.id))
                updateSaveCount(post.id, post.saveCount+1)
            }
        }
        Log.d(TAG, "Paco: onSave after ${post.id}, is saved=$isSaved")
    }

    fun onRefresh() {}



    @SuppressLint("CommitPrefEdits")
    fun fetchAndStoreSavedPostIds(context: Context) {
        val sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        Log.d(TAG, "Paco: fetchAndStoreSavedPostIds")
        viewModelScope.launch() {
            context.savedPostIDs.edit { it.clear() }
            userService.user.collect { user ->
                Log.d(TAG, "Paco: collect user")
                val savedPostsId = user?.savedPostIds
                if (savedPostsId != null) {
                    for (postId in savedPostsId) {
                        Log.d(TAG, "Paco: save $postId")
                        savedPostIds.add(postId)
//                        storeSavedPostId(context, postId, true)
                    }
                }
                Log.d(TAG, "Paco: fetch and store Post = ${savedPostIds.toList()}")
            }
//            sharedPref.edit()
//                .putStringSet("savedPostIds", savedPOstIds)
//                .apply()
        }
    }

//    fun fetchEvents(date: Timestamp = Timestamp.now()) {
//        Log.d(TAG, "Paco: today is $date")
//        viewModelScope.launch {
//            val events = service.getEvents(date)
//            Log.d(TAG, "Paco: events = $events")
//
//        }
//    }

}

//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class ServiceModule {
//    @Binds
//    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
//
//    …
//}
