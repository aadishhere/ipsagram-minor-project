package com.aadish.ipsagram.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aadish.ipsagram.ui.components.CategoryButton
import com.aadish.ipsagram.ui.components.EventDate
import com.aadish.ipsagram.ui.components.EventHashTag
import com.aadish.ipsagram.ui.components.EventPhoto
import com.aadish.ipsagram.ui.components.EventTitle
import com.aadish.ipsagram.data.model.Event
import com.aadish.ipsagram.data.model.postInfo
import com.aadish.ipsagram.ui.navigation.Graph
import com.aadish.ipsagram.ui.theme.FinalProjectTheme
import com.aadish.ipsagram.ui.theme.cardColor
import com.aadish.ipsagram.ui.theme.darkBackground
import com.aadish.ipsagram.ui.theme.darkerBackground
import com.aadish.ipsagram.ui.theme.white
import com.aadish.ipsagram.ui.viewModels.EventViewModel
import com.aadish.ipsagram.ui.viewModels.PostViewModel
import com.google.firebase.Timestamp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventScreen(navController: NavController, eventViewModel: EventViewModel,postViewModel: PostViewModel = hiltViewModel()) {
    val eventList = eventViewModel.eventList
val postList = postViewModel.postList.collectAsStateWithLifecycle()
    val user = eventViewModel.user
    val savedEventIds = user.savedEventIds

    var selectedTabIndex = remember { mutableIntStateOf(0) }

//    val scaffoldState = rememberScaffoldState()
    val tabItems = listOf(
        TabItem(title = "Featured"),
        TabItem(title = "Saved")
    )
    var pagerState = rememberPagerState {
        tabItems.size
    }
//    val eventList = eventViewModel.fetchEvents() ?: mutableListOf<Event>()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
        ){
//            EventHeading()
//            Spacer(     // horizontal divisor
//                modifier = Modifier.imePadding()
//                    .fillMaxWidth()
//                    .height(1.dp)
//                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
//            )
//            EventList(events = eventList.toList(), navController, eventViewModel)
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                contentColor = white
            ) {
                val isSelected =
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        modifier = Modifier.background(darkerBackground),
                        text = {
                            Text(
                                text = item.title,
                                fontWeight = if (selectedTabIndex.value == index) FontWeight.Bold else FontWeight.Light,
                            )
                        },

                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                ) {index->
if (index==0){
    Column(modifier = Modifier, verticalArrangement = Arrangement.Top) {
        EventList(postList = postList.value, navController,postViewModel)

    }
    }
    else if(index==2){
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)) {

        }
    }

            }




        }
    }
}

@Composable
fun EventHeading() {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "University Event",
            style = TextStyle(
//                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = white
            )
        )
    }
}

data class TabItem(
    val title: String
)

@Composable
fun EventUI(event: Event, navController: NavController, eventViewModel: EventViewModel) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .padding(start = 8.dp, top = 12.dp, end = 6.dp)
            .width(176.dp)
            .background(darkBackground)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
//                navController.currentBackStackEntry?.savedStateHandle?.set(
//                    key = "event",
//                    value = event
//                )
                eventViewModel.addEvent(event)
                navController.navigate(Graph.EVENT_DETAILS)
            }
    ) {
        EventPhoto(event = event)
        CategoryButton(value = event.category)
        EventTitle(value = event.title)
        EventDate(event = event)
        EventHashTag(event = event)
    }
}

@Composable
fun EventList(postList:List<postInfo>, navController: NavController,postViewModel: PostViewModel) {
LazyColumn {
    items(postList.reversed()){post->
       postCard(title =post.title, content = post.content ,post.category,navController,postViewModel,post.time,post.link)
    }
}
}

@Composable
fun PostUI(postViewModel: PostViewModel = hiltViewModel(),navController: NavController) {
    val postInfoState = postViewModel.postInfoState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()

            .background(darkerBackground)
            .padding(16.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,tint = Color.White,modifier = Modifier
                .size(30.dp)
                .clickable(onClick = { navController.navigate(Graph.HOME) }))
            Text(text = postInfoState.value.category, fontSize = 15.sp, color = Color.White)

        }

//
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = postInfoState.value.title,color = Color.White, fontWeight = FontWeight.Bold,modifier = Modifier.fillMaxWidth(), fontSize = 20.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = postInfoState.value.content,color = Color.White,modifier = Modifier.fillMaxWidth(), fontSize = 15.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = postInfoState.value.link,modifier = Modifier
            .fillMaxWidth()
            , fontSize = 15.sp, color = Color.White)
//        Text(text = "posten On:"+postInfoState.value.time.toString(),modifier = Modifier
//            .fillMaxWidth()
//            .padding(4.dp), fontSize = 10.sp, color = Color.White, textAlign = TextAlign.End)


    }
}


@Preview
@Composable
fun EventScreenPreview() {

//    Column(
//        Modifier
//            .fillMaxSize()
//            .background(Color.White)) {
//        postCard()
//    }

//    FinalProjectTheme(darkTheme = true) {
//        val navController = rememberNavController()
//        Scaffold(
//            bottomBar = { BottomBar(navController = navController) },
//            content = { padding ->
//                Column(
//                    modifier = Modifier
//                        .padding(padding)
//                ){
//                    EventScreen(navController = navController, eventViewModel = viewModel())
//                }
//            }
//        )
//    }
}


@Composable
fun postCard(title: String , content:String,category:String,navController: NavController,postViewModel: PostViewModel= hiltViewModel(),timestamp: Timestamp,link:String) {
Card(modifier = Modifier
    .fillMaxWidth()
    .padding(16.dp)
    .height(100.dp)
    .clickable(onClick = {
        postViewModel.toUpdateState(title, category, content, timestamp, link)
        navController.navigate(Graph.POST_INFO)
    }),shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(
        containerColor = cardColor
    )
    ) {
Column(
    Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
    Text(text = title,color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis,modifier = Modifier.fillMaxWidth(), fontSize = 28.sp)
Spacer(modifier = Modifier.height(8.dp))
    Text(text = content,color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis,modifier = Modifier.fillMaxWidth())

}
}
}