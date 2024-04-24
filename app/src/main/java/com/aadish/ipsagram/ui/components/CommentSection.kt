package com.aadish.ipsagram.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aadish.ipsagram.data.model.Comment
import com.aadish.ipsagram.ui.theme.FinalProjectTheme
//import eu.bambooapps.material3.pullrefresh.pullRefresh

/*
* The Comment Section
* - Display all the comments
* - show text when there is no comments
*
*/
@Composable
fun CommentSection(comments: Array<Comment>, modifier: Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    if (comments.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        keyboardController?.hide()
                    },
                ),
            contentPadding = PaddingValues(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(comments.toList()) { comment ->
                CommentCard(comment)
            }
        }
    } else {
        Column(modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    keyboardController?.hide()
                },
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No Comment Yet")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CommentSectionPreview() {
    FinalProjectTheme(darkTheme = true) {
        val comments = mutableListOf<Comment>()
        for (i in 1..5) {
            comments.add(Comment("Hi", ))
        }
        Surface {
            CommentSection(comments = comments.toTypedArray(), modifier = Modifier)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EmptyCommentSectionPreview() {
    FinalProjectTheme(darkTheme = true) {
        val comments = mutableListOf<Comment>()

        Surface {
            CommentSection(comments = comments.toTypedArray(), modifier = Modifier)
        }
    }
}
