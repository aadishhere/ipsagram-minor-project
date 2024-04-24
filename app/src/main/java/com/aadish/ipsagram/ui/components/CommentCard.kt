package com.aadish.ipsagram.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aadish.ipsagram.data.model.Comment
import com.aadish.ipsagram.ui.theme.FinalProjectTheme
import com.aadish.ipsagram.ui.theme.grey
import java.util.Date
import java.util.concurrent.TimeUnit


//val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
@Composable
fun CommentCard(comment: Comment) {
    val currentDate = remember { Date() }
    val timeDifference = remember { mutableStateOf("") }

    LaunchedEffect(comment.time) {
        val difference = currentDate.time - comment.time.toDate().time

        timeDifference.value = when {
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
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()) {
        Card (
            modifier = Modifier
                .fillMaxWidth(),
            shape = RectangleShape,
//            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.surfaceVariant),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        ) {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ){
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Log.d(TAG, "Paco: is Writer = ${comment}")
                    if (comment.sameWriter) {
                        Text(text = "Writer", color = Color.Cyan, fontSize = 16.sp)
                    } else {
                        Text(text = "Anonymous", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = timeDifference.value,
                        color = grey,
                        fontSize = 14.sp
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(comment.content)
            }
        }

        Spacer(     // horizontal divisor
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        )
    }

    // TODO: Long press delete
}

@Preview(showBackground = true)
@Composable
fun CommentCardPreview() {
    FinalProjectTheme(darkTheme = true) {
        CommentCard(Comment(content = "HI"))
    }
}