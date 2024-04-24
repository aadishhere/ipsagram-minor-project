package com.aadish.ipsagram.ui.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aadish.ipsagram.data.model.User
import com.aadish.ipsagram.ui.components.PostCard
import com.aadish.ipsagram.ui.navigation.Graph
import com.aadish.ipsagram.ui.theme.FinalProjectTheme
import com.aadish.ipsagram.ui.theme.blue
import com.aadish.ipsagram.ui.viewModels.PostViewModel
import com.aadish.ipsagram.ui.theme.darkBackground
import com.aadish.ipsagram.ui.theme.white
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommunityScreen(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel(),
    openPostDetailScreen: (String) -> Unit) {
    /*
    * Features:
    *   1. View PostFeed
    *      - No post
    *      - Render PostFeed
    *
    *   2. Refresh screen
    *      - display reloading
    *      - refresh animation
    *      - update PostFeed
    *
    *   3. Navigate Regular/Popular
    *
    *   4. Create new post with button
    *
    */
    Log.d(TAG, "Paco: render CommunityScreen")

    val context = LocalContext.current
    var posts = viewModel.posts.collectAsStateWithLifecycle(initialValue = emptyList())
    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = User())

    val savedPostIds = user.value!!.savedPostIds
    val myPostIds = user.value!!.myPostIds

    Scaffold (
        modifier = Modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = darkBackground),
                title = {
                    Text(
                        text = "Community",
                        style = TextStyle(
//                fontFamily = FontFamily.Default,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = white
                        )
                    )
                }
            )
        },
        floatingActionButton = {navController.navigate(Graph.POST_CREATE)}
    ) { it ->
        Column(modifier = Modifier.padding(it)) {

            Spacer(     // horizontal divisor
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )

            // state for listen to refreshing
            var isRefreshing by remember {
                mutableStateOf(false)
            }

            val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
                isRefreshing = true
                viewModel.onRefresh()
                isRefreshing = false
            })

            if (posts.value.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        //                    .padding(it)
                        .fillMaxSize()
                        .pullRefresh(state),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(posts.value) {post ->
                        if (!post.deleted) {
                            PostCard(
                                Modifier, post, viewModel, navController,
                                isSaved = post.id in savedPostIds,
                                isMyPost = post.id in myPostIds,
                                inDetailsScreen = false,
                                openPostDetailScreen
//                            viewModel::onSaveClicked,
//                            ,
//                            viewModel::incrementView,
//                            viewModel::getTimeDifference,
//                        isMyPost = (post.id in myPostIds)
                            )
                        }

                    }
                }
            }else {
                LazyColumn(
                    modifier = Modifier
                        //                    .padding(it)
                        .fillMaxSize()
                        .pullRefresh(state),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        Text("No Post Yet", modifier = Modifier)
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing, state = state,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

   }
}

//fun checkPostFeed(): Boolean {
//    return true
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(200.dp),
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "Create Post",
            tint = blue
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    FinalProjectTheme(darkTheme = true) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomBar(navController) },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ){
                    CommunityScreen(navController, openPostDetailScreen = {s->})
//                    val navBackStackEntry by navController.currentBackStackEntryAsState()
//                    val currentDestination = navBackStackEntry?.destination
//
//                    Text(text = currentDestination?.route?:"hi")
                }
            }
        )
    }
}