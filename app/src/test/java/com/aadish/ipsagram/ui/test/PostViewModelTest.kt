//package com.example.finalproject.ui.test
//
//import android.content.Context
//import android.os.Build.VERSION_CODES.Q
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.testTag
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithTag
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.navigation.compose.rememberNavController
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.finalproject.data.model.Post
//import com.example.finalproject.data.service.module.AppModule
//import com.example.finalproject.ui.viewModels.PostViewModel
//import com.google.firebase.FirebaseApp
//import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
//import eu.bambooapps.material3.pullrefresh.pullRefresh
//import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.robolectric.annotation.Config
//
//@RunWith(AndroidJUnit4::class)
//@Config(sdk = [Q])
//class PostViewModelTest {
//    init {
//        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext())
//    }
//    private val viewModel = PostViewModel(AppModule.provideStorageService())
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Mock
//    private lateinit var mockContext: Context
//
//    @Test
//    fun fetchingTest() {
//            composeTestRule.setContent {
//                PostsViewModelTESTUI(viewModel)
//            }
//            composeTestRule
//                .onNodeWithTag("Test")
//                .assertExists()
//
//
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PostsViewModelTESTUI(viewModel: PostViewModel) {
//    val navController = rememberNavController()
//    val postList = viewModel.posts.collectAsStateWithLifecycle(initialValue = emptyList<Post>())
//
//    Scaffold (
//        topBar = { TopAppBar(title = { Text("Community") }) }
//    ) {
//        Column(modifier = Modifier.padding(it)) {
//
//            Spacer(     // horizontal divisor
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(1.dp)
//                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
//            )
//
//            // state for listen to refreshing
//            var isRefreshing by remember {
//                mutableStateOf(false)
//            }
//
////            val (checkPostFeed, setCheckPostFeed) = remember {
////                mutableStateOf(checkPostFeed())
////            }
//            val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
//                isRefreshing = true
////                setCheckPostFeed(!checkPostFeed)
//                isRefreshing = false
//            })
//
//            LazyColumn(
//                modifier = Modifier.testTag("Test")
//                    //                    .padding(it)
//                    .fillMaxSize()
//                    .pullRefresh(state),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                items(postList.value) { post ->
//                    println("posts: $post")
//                    Text(text = post.title, Modifier.testTag("Test"))
//                }
//            }
//
//            PullRefreshIndicator(
//                refreshing = isRefreshing, state = state,
//                modifier = Modifier
//                    .fillMaxSize()
//            )
//        }
//
//    }
//}