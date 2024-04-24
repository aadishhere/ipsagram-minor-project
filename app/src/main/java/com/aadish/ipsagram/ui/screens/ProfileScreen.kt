package com.aadish.ipsagram.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.aadish.ipsagram.data.Preferences
import com.aadish.ipsagram.data.model.Post
import com.aadish.ipsagram.data.model.Response
import com.aadish.ipsagram.data.model.User
import com.aadish.ipsagram.ui.components.PopUpDialog
import com.aadish.ipsagram.ui.components.PostCard

import com.aadish.ipsagram.ui.theme.FinalProjectTheme
import com.aadish.ipsagram.ui.theme.darkBackground
import com.aadish.ipsagram.ui.theme.grey
import com.aadish.ipsagram.ui.theme.white
import com.aadish.ipsagram.ui.viewModels.PostViewModel
import com.aadish.ipsagram.ui.viewModels.ProfileViewModel
import com.aadish.ipsagram.util.Utils.Companion.showMessage

@Composable
fun UserGreetings(username: String) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "${username}'s Page",
            style = TextStyle(
//                fontFamily = FontFamily.Default,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = white
            )
        )
        Text(
            text = "IES IPS Academy",
            style = TextStyle(
//                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = grey
            ),
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
        )
    }
}


@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
//    scaffoldState: ScaffoldState,
//    coroutineScope: CoroutineScope,
    signOut: () -> Unit,
) {
    val context = LocalContext.current

//    fun showRevokeAccessMessage() = coroutineScope.launch {
//        val result = scaffoldState.snackbarHostState.showSnackbar(
//            message = "You need to re-authenticate before revoking the access.",
//            actionLabel = "Sign out"
//        )
//        if (result == SnackbarResult.ActionPerformed) {
//            signOut()
//        }
//    }

    when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Success -> {
            val isAccessRevoked = revokeAccessResponse.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked) {
                    showMessage(context, "Your access has been revoked.")
                }
            }
        }
        is Response.Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)
                if (e.message == "This operation is sensitive and requires recent authentication. Log in again before retrying this request.") {
                    signOut()
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    postViewModel: PostViewModel = hiltViewModel(),
    openPostDetailScreen: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedTabIndex = remember { mutableIntStateOf(0) }

    val tabItems = listOf(
        TabItem(title = "Post"),
        TabItem(title = "Save")
    )

    var pagerState = rememberPagerState { tabItems.size }

    var openMenu by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("")}
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showDeleteAccDialog by remember { mutableStateOf(false) }

    Preferences.getUserName(context) {
        username = it ?: "User"
    }

    Surface(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(darkBackground)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
        ){
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = darkBackground,
                    titleContentColor = white,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ""
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    profileViewModel.signOut()

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Logout,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                },
                actions = {
                    DropdownMenu(
                        expanded = openMenu,
                        onDismissRequest = {
                            openMenu = !openMenu
                        },
                        modifier = Modifier
                            .background(darkBackground)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Sign Out",
                                    color = white
                                )
                            },
                            onClick = {
                                  showSignOutDialog = true
//                                profileViewModel.signOut()
//                                openMenu = !openMenu
                            },
                            colors = MenuDefaults.itemColors(darkBackground)
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Delete Account",
                                    color = white
                                )
                            },
                            onClick = {
                                showDeleteAccDialog = true
//                                profileViewModel.revokeAccess()
//                                openMenu = !openMenu
                            }
                        )

                        if (showSignOutDialog) {
                            PopUpDialog(
                                title = "Sign Out",
                                body = "Are you sure you want to sign out?",
                                actionText = "Sign Out",
                                onClose = {
                                    showSignOutDialog = false
                                    openMenu =! openMenu
                                },
                                onDismiss = {
                                    profileViewModel.signOut()
                                    openMenu = !openMenu
                                    showSignOutDialog = false // Hide the delete dialog
                                    showMessage(context, "Successfully signed out")
                                }
                            )
                        }

                        if (showDeleteAccDialog) {
                            PopUpDialog(
                                title = "Delete Account",
                                body = "Are you sure you want to delete your account? Once you delete your account, it can't be restored.",
                                actionText = "Delete Account",
                                onClose = {
                                    showSignOutDialog = false
                                    openMenu =! openMenu
                                },
                                onDismiss = {
                                    profileViewModel.signOut()
                                    openMenu = !openMenu
                                    showSignOutDialog = false // Hide the delete dialog
//                                    showMessage(context, "Successfully signed out")
                                }
                            )
                        }
                    }
                }
            )

            UserGreetings(username = username)

            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                contentColor = white
            ) {
                val isSelected =
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        modifier = Modifier.background(darkBackground),
                        text = {
                            Text(
                                text = item.title,
                                fontWeight = if (selectedTabIndex.value == index) FontWeight.Bold else FontWeight.Light,
                            )
                        },
//                        selectedContentColor = white
                    )
                }
            }
            when (selectedTabIndex.value) {
                0 -> {
                    MyPostList(navController = navController, viewModel = postViewModel, openPostDetailScreen = openPostDetailScreen)

                    //                    Column (
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(darkBackground),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ){
//                        Text(
//                            text = "No Posts",
//                            color = white
//                        )
//                    }
                }
                1 -> {
                    SavedPostList(navController = navController, viewModel = postViewModel, openPostDetailScreen = openPostDetailScreen)
//                    Column (
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(darkBackground),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ){
//                        Text(
//                            text = "No Saved",
//                            color = white
//                        )
//                    }
                }
            }
        }

    }

    RevokeAccess(
        signOut = {
            profileViewModel.signOut()
        }
    )
}

@Composable
fun MyPostList(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
    navController: NavController,
    openPostDetailScreen: (String) -> Unit
) {
//    val savedPostsIds by remember {
//        mutableStateOf(listOf<String>())
//    }

    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = User())
//    val savedPostIds = user.value!!.savedPostIds
//    val myPostIds = user.value!!.myPostIds

//    if (myPostIds.isNotEmpty()){
//        LazyColumn(modifier = modifier.fillMaxSize(),
////            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally) {
//            items(myPostIds.reversed()) {postId ->
////                val post = viewModel.fetchPost(postId).collectAsStateWithLifecycle(initialValue = Post())
////                if (post.value?.deleted == false) {
////                    PostCard(
////                        Modifier, post?.value ?: Post(), viewModel, navController,
////                        isSaved = post.value?.id in savedPostIds,
////                        isMyPost = post.value?.id in myPostIds,
////                        inDetailsScreen = false,
////                        openPostDetailScreen
////                    )
////                }
//            }
//        }
//    } else {
//        LazyColumn(modifier = modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally) {
//            item {
//                Text("No Post Created Yet", modifier = Modifier)
//            }
//        }
//    }

//    viewModel.savedPOstIds.li
}


@Composable
fun SavedPostList(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
    navController: NavController,
    openPostDetailScreen: (String) -> Unit
) {
//    val savedPostsIds by remember {
//        mutableStateOf(listOf<String>())
//    }

    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = User())
    val savedPostIds = user.value!!.savedPostIds
    val myPostIds = user.value!!.myPostIds

    if (savedPostIds.isNotEmpty()){
        LazyColumn(modifier = modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            items(savedPostIds.reversed()) {postId ->
//                val post = viewModel.fetchPost(postId).collectAsStateWithLifecycle(initialValue = Post())
//                val isDeleted = post.value?.deleted ?: false
                if (false) {
//                    PostCard(
//                        Modifier, post.value ?: Post(), viewModel, navController,
//                        isSaved = post.value?.id in savedPostIds,
//                        isMyPost = post.value?.id in myPostIds,
//                        inDetailsScreen = false,
//                        openPostDetailScreen
//                    )
                }


            }
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Text("No Saved Post Yet", modifier = Modifier)
            }
        }
    }

//    viewModel.savedPOstIds.li
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    FinalProjectTheme {
//        ProfileScreen()
    }
}