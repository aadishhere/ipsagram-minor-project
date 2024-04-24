package com.aadish.ipsagram.ui.screens


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aadish.ipsagram.ui.components.CommentSection
import com.aadish.ipsagram.ui.components.PostCard
import com.aadish.ipsagram.data.model.Comment
import com.aadish.ipsagram.data.model.Post
import com.aadish.ipsagram.data.model.User
import com.aadish.ipsagram.ui.theme.FinalProjectTheme
import com.aadish.ipsagram.ui.theme.white
import com.aadish.ipsagram.ui.viewModels.PostViewModel
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun PostDetailsScreen(
    postID: String,
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {
    Log.d(TAG, "Paco: rendering post Detail: $postID")
    // TODO: Obtain comments given post id
    val context = LocalContext.current
    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = User())
    val savedPostIds = user.value!!.savedPostIds

//    var post = viewModel.fetchPost(postID).collectAsStateWithLifecycle(initialValue = Post())
    var comments = viewModel.fetchComments(postID).collectAsStateWithLifecycle(initialValue = listOf<Comment>())
        .value.sortedBy { it.time }
//    Log.d(TAG, "postView fetched: $post")
//    Log.d(TAG, "postView isSaved: ${postView.isSaved}")

//    val post = viewModel.getPost(postID).collectAsStateWithLifecycle(initialValue = Post())
    
    // States
//    val (post, setPost) = remember {
//        mutableStateOf(fetchPost(postID, context))
//    }

//    val (comments, setComments) = remember {
//        mutableStateOf(fetchComments(0, post.value?.commentCount ?: 0))
//    }

//    val (commentCount, setCommentCount) = remember {
//        mutableIntStateOf(post.value?.commentCount ?:0)
//    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        isRefreshing = true

        // check weather post have new comments
//        val tmpPost = fetchPost(postID, context)
//        if (0 != commentCount) {
////            setPost(tmpPost)
//            setComments(fetchComments(0, 0))
//        }

        isRefreshing = false
    })
//    Column (modifier = Modifier.fillMaxSize()) {

        Scaffold (
            modifier = Modifier.imePadding(),
            topBar = { PostDetailTopBar(navController) },
//        bottomBar = {PostDetailBottomBar(Modifier,post.value?:Post(), viewModel::onCommentSubmit)}
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(     // horizontal divisor
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                )
//                PostCard(
//                    Modifier, post.value?: Post(), viewModel, navController,
//                    isSaved = post.value?.id in user.value!!.savedPostIds,
//                    isMyPost = post.value?.id in user.value!!.myPostIds,
//                    inDetailsScreen = true, {}
//                )
//                Spacer(     // horizontal divisor
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(16.dp)
//                        .background(color = MaterialTheme.colorScheme.background)
//                )
//                Spacer(     // horizontal divisor
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(1.dp)
//                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
//                )
                CommentSection(comments.toTypedArray(),
                    modifier = Modifier
                        .fillMaxHeight()
                        .pullRefresh(state))

//                PostDetailBottomBar(Modifier.align(Alignment.End),post.value?:Post(), viewModel::onCommentSubmit)
//            Text("Something went wrong.")
//            Text("Please try again.")
            }
        }

//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailTopBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = "", fontSize = 18.sp) },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBackIos,
                    contentDescription = "Back",
                    tint = white
                )
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class, ExperimentalFoundationApi::class
)
@Composable
fun PostDetailBottomBar(modifier: Modifier, post: Post, onCommentSubmit: (Context, Post, String) -> Unit) {
    val context = LocalContext.current
    val (commentInputText, setCommemtInputText) = remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val view = LocalView.current
    fun onSubmit() {
        onCommentSubmit(context, post, commentInputText)
        setCommemtInputText("")
        keyboardController?.hide()
    }
    val windowInsets = WindowInsets.isImeVisible
    val bringIntoViewRequester = remember { BringIntoViewRequester()}


//    Column {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = commentInputText,
                    onValueChange = setCommemtInputText,
                    placeholder = {Text(text = "Write your comments", color=Color.Black)},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {onSubmit()}
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    colors = TextFieldDefaults.colors(
                        Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.LightGray,
                    )
                )
            }
//        Spacer(modifier = Modifier)
            IconButton(
                onClick = {onSubmit()}
            ) {
                Icon(
                    imageVector = Icons.Rounded.Send,
                    contentDescription = "Send",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }

        }
//    }

}


@Preview(showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    FinalProjectTheme(darkTheme = true) {
        PostDetailsScreen("6mAcX7QrhhdETA5piHvx",
            navController = NavController(LocalContext.current),
//            CommunityViewModel(SavedStateHandle(), PostServiceTestImpl(), UserServiceImpl())
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PostDetailScreenNoCommentPreview() {
//    FinalProjectTheme(darkTheme = true) {
//        val comments = mutableListOf<Comment>()
//
//
//        PostDetailsScreen(0, navController = NavController(LocalContext.current))
//    }
//}