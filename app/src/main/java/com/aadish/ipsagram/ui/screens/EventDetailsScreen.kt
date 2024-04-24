package com.aadish.ipsagram.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.aadish.ipsagram.ui.components.CategoryButton
import com.aadish.ipsagram.ui.components.CategoryHashtag
import com.aadish.ipsagram.ui.components.DetailedView
import com.aadish.ipsagram.ui.components.EventDescription
import com.aadish.ipsagram.ui.components.EventDetailPhoto
import com.aadish.ipsagram.ui.viewModels.EventViewModel
import com.aadish.ipsagram.ui.components.EventTitle
import com.aadish.ipsagram.ui.components.QuickView
import com.aadish.ipsagram.ui.theme.darkerBackground
import com.aadish.ipsagram.ui.theme.white

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    navController: NavHostController,
    eventViewModel: EventViewModel,
    startActivity: (Intent) -> Unit
) {
//    val userInfoSnap = /* TODO: Add code to fetch user info snapshot */
//    val isEventSaved = /* TODO: Add code to determine if the event is saved */
    val context = LocalContext.current
    val event = eventViewModel.event
    val user = eventViewModel.user
    var isSaved by remember {
        mutableStateOf(event?.id in user.savedEventIds)
    }

    Scaffold(
        contentColor = darkerBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = darkerBackground,
                    titleContentColor = white,
                ),
                title = {
                    Text(
                        text = "",
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = white
                        )
                    }
                },
//                actions = {
//                    IconButton(
//                        onClick = {
//                            navController.navigate("report_graph/${event?.id}")
//                        }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Warning,
//                            contentDescription = "Report"
//                        )
//                    }
//                }
            )
        },
        content = {
//            EventDetailsNavGraph(navController = rememberNavController())
            Box(modifier = Modifier
                .fillMaxSize()
                .background(darkerBackground)
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    EventDetailPhoto(event = event!!)
                    Column(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp)
                    ) {
                        CategoryButton(value = event.category)
                        EventTitle(value = event.title, isDetail = true)
                        CategoryHashtag(event = event)
                        Spacer(modifier = Modifier.height(20.dp))
                        QuickView(event = event)
                        EventDescription(event = event)
                        DetailedView(event = event)
                    }
                    Spacer(modifier = Modifier
                        .height(20.dp)
                    )
                    Row(
                        modifier = Modifier
//                            .align(Alignment.BottomStart)
                            .padding(start = 20.dp, bottom = 45.dp)
                    ) {
                        Button(
                            onClick = {
                                Intent(Intent.ACTION_VIEW).also {
                                    it.data = Uri.parse(event.registerLink)
                                    startActivity(it)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.76f)
                                .height(48.dp),
                            shape = RoundedCornerShape(10.dp),
                            enabled = (!event.isExpired and event.registerLink.isNotEmpty())
//                            colors = Color(hexStringToColor(buttonColor(event.category)))
                        ) {
                            Text(
                                text = "Register",
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.W500
                            )
                        }
                        IconButton(
                            onClick = {
                                eventViewModel.onSaveClicked(context, event.id, isSaved)
                                isSaved = !isSaved
                            },
                            modifier = Modifier.padding(start = 18.dp)
                        ) {
                            Icon(
                                imageVector = (
                                    if (isSaved)
                                        Icons.Default.Bookmark
                                    else
                                        Icons.Default.BookmarkBorder
                                ),
                                contentDescription = "Save event",
                                modifier = Modifier
                                    .size(150.dp),
                                tint = Color.White
                            )
                        }
//                        EventSaveButton(
//                            controller = /* TODO: Add the event controller */,
//                            currentUser = /* TODO: Add the current user */,
//                            saved = isEventSaved,
//                            modifier = Modifier.padding(start = 18.dp)
//                        )
                    }
                }
            }
        }
    )
}

@Composable
fun EventSaveButton(isSaved: Boolean,
    onSaveClicked: (Context, String, Boolean) -> Unit
) {
    if (isSaved) {

    }
}



//@Preview(showBackground = true)
//@Composable
//fun EventDetailsPagePreview() {
//    FinalProjectTheme(darkTheme = true) {
//        EventDetailsScreen(navController = rememberNavController(), eventViewModel = EventViewModel()
//        )
//    }
//}