package com.aadish.ipsagram.ui.components

//import android.content.Context
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AddReaction
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.LaptopMac
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
//import com.example.finalproject.data.checkSavedPost
import com.aadish.ipsagram.ui.theme.FinalProjectTheme
import com.aadish.ipsagram.data.model.Post
import com.aadish.ipsagram.data.model.PostCategory
import com.aadish.ipsagram.data.model.User
import com.aadish.ipsagram.ui.theme.darkBackground
import com.aadish.ipsagram.ui.theme.darkerBackground
import com.aadish.ipsagram.ui.theme.green
import com.aadish.ipsagram.ui.theme.grey
import com.aadish.ipsagram.ui.theme.pink
import com.aadish.ipsagram.ui.theme.white
import com.aadish.ipsagram.ui.viewModels.PostViewModel
import com.aadish.ipsagram.util.Utils
import com.google.firebase.Timestamp
import java.util.Date

@Composable
fun PostCard(
    modifier: Modifier,
    post: Post,
    postViewModel: PostViewModel,
    navController: NavController,
    isSaved: Boolean,
    isMyPost: Boolean = true,
    inDetailsScreen: Boolean,
    openPostDetailScreen: (String) -> Unit,
) {
    Column {
        val interactionSource = remember { MutableInteractionSource() }

        var showDeleteDialog by remember { mutableStateOf(false) }
        val context = LocalContext.current

        Card(
            // Define the layout and style of the card
            modifier = modifier
                .fillMaxWidth()
                .background(darkerBackground),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        ) {
            // Define the content of the card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(darkBackground)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            openPostDetailScreen(post.id)           // navigate to PostDetail Screen
                            postViewModel.incrementView(post.id)
                        }
                    )
            ) {
                // Display Header
                PostCardHeader(
                    post.category, post.time.toDate(),
                    postViewModel::getTimeDifference,
                    menuItems = listOf(
                        Pair("Report") { navController.navigate("report_graph/${post.id}") },
                        if (isMyPost) Pair("Delete") {
//                            postViewModel.onDelete(post.id)
                            showDeleteDialog = true
                            if (inDetailsScreen) navController.navigateUp()                              // go back if in detail screen.
                        } else null         // is null when the post is not belongs to user
                    )
                )

                // Show the delete dialog if the state variable is true
                if (showDeleteDialog) {
                    PopUpDialog(
                        title = "Delete Post",
                        body = "Are you sure you want to delete this post?",
                        actionText = "Delete",
                        onClose = {
                            showDeleteDialog = false
                        },
                        onDismiss = {
                            postViewModel.onDelete(post.id)
                            showDeleteDialog = false // Hide the delete dialog
                            Utils.showMessage(context, "Post successfully deleted")
                        }
                    )
                }


                // Display the user name and the post time
                Text(
                    text = post.title,
                    modifier = Modifier .padding(bottom=8.dp),
                    color = white,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                )

                // Display the post content
                Text(
                    text = post.content,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    color = grey,
                    fontSize = 16.sp,
                    maxLines = if (inDetailsScreen) Int.MAX_VALUE else 2,
                    overflow = if (inDetailsScreen) TextOverflow.Visible else TextOverflow.Ellipsis
                )

                // Display Post stat count
                PostCardStatus(post, isSaved, postViewModel::onSaveClicked)

            }
        }

        // Bottom border horizontal divisor
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}


@Composable
fun PostCardHeader(
    category: String,
    date: Date,
    getTimeDifference: (Date, (String) -> Unit) -> Unit,
    menuItems: List<Pair<String, () -> Unit>?>
) {
    var timeDifference by remember { mutableStateOf("") }

    getTimeDifference(date) {
        timeDifference = it
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        /* ==== Category Icon ==== */
        Icon(
            imageVector = if (category == "Just Talk") Icons.Outlined.AddReaction else Icons.Outlined.LaptopMac,
            contentDescription = "Post Category",
            tint = if (category == "Just Talk") green else pink
        )
        Spacer(modifier = Modifier.width(8.dp))

        /* ==== Category Name ==== */
        Text(
            text = category,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))

        /* ==== Post time ==== */
        Text(
            text = timeDifference,
            color = grey,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(8.dp))

        /* ==== More Action ==== */
        DropDownMenu(menuItems)
    }
}


@Composable
fun PostCardStatus(
    post: Post,
    isSaved: Boolean,
    onSaveClicked: (Context, Post, Boolean) -> Unit
) {
    val context = LocalContext.current

    // Display the post stats
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        // Display the number of views
        Row(verticalAlignment = Alignment.CenterVertically ) {
            Icon(
                imageVector = Icons.Outlined.RemoveRedEye,
                contentDescription = "Views"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = post.viewCount.toString(),
                fontSize = 16.sp
            )
        }

        // Display the number of comments
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Comment,
                contentDescription = "Comments"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = post.commentCount.toString(),
                fontSize = 16.sp
            )
        }

        // Display the number of shares
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(
                onClick = {
                    Log.d(TAG, "Paco: Click Saved")
                    onSaveClicked(context, post, isSaved)
                })
        ) {
            Icon(
                imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Save Post"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = post.saveCount.toString(),
                fontSize = 16.sp
            )

        }
    }
}

@Composable
fun PostDropDownMenu(
    postId: String,
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
    ) {

    val user = viewModel.user.collectAsStateWithLifecycle(initialValue = User())
    val myPostIds = user.value!!.myPostIds
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {

        var expended by remember {
            mutableStateOf(false)
        }
        var showDeleteDialog by remember { mutableStateOf(false) }

        IconButton(
            onClick = {
                /* TODO: Add code to navigate to 'report' screen */
//                    navController.navigate(Graph.REPORT)
                expended = true

            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Report"
            )
        }
        DropdownMenu(
            expanded = expended,
            onDismissRequest = {
                expended = false
            },
            modifier = Modifier
        ) {
            DropdownMenuItem(
                text = { Text("Report") },
                onClick = { navController.navigate("report_graph/$postId") }
            )
            if (myPostIds.contains(postId)) {
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
//                        viewModel.onDelete(postId)
                        showDeleteDialog = true
                    }
                )
            }

            // Show the delete dialog if the state variable is true
            if (showDeleteDialog) {
                PopUpDialog(
                    title = "Delete Post",
                    body = "Are you sure you want to delete this post?",
                    actionText = "Delete",
                    onClose = {
                        showDeleteDialog = false
                        expended =! expended
                    },
                    onDismiss = {
                        viewModel.onDelete(postId)
                        expended = !expended
                        showDeleteDialog = false // Hide the delete dialog
                        Utils.showMessage(context, "Post successfully deleted")
                    }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    FinalProjectTheme(darkTheme = true) {
        val post = Post(
            "TEST_POST_ID",
            PostCategory.ACADEMIC.name,
            "This is title",
            Timestamp(Date(0)),
            "User 00",
            "content",
            false,
            5,
            0,
            0,
        )

        PostCard(
            modifier = Modifier,
            post, hiltViewModel(),
            NavController(LocalContext.current),
            false,
            false, false, {}
        )

    }
}
