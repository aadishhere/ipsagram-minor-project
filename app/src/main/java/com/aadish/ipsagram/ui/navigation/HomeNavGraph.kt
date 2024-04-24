package com.aadish.ipsagram.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aadish.ipsagram.ui.components.BottomBarScreen
import com.aadish.ipsagram.ui.screens.CommunityScreen
import com.aadish.ipsagram.ui.screens.EventDetailsScreen
import com.aadish.ipsagram.ui.screens.EventScreen
import com.aadish.ipsagram.ui.screens.PostCreateScreen
import com.aadish.ipsagram.ui.screens.PostDetailsScreen
import com.aadish.ipsagram.ui.screens.PostUI
import com.aadish.ipsagram.ui.screens.ProfileScreen
import com.aadish.ipsagram.ui.screens.ReportScreen
import com.aadish.ipsagram.ui.viewModels.EventViewModel
import com.aadish.ipsagram.ui.viewModels.PostViewModel
import com.aadish.ipsagram.ui.viewModels.ProfileViewModel

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    startActivity: (Intent) -> Unit,
    postViewModel: PostViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val eventViewModel: EventViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()


    postViewModel.fetchAndStoreSavedPostIds(context)
    eventViewModel.fetchAndStoreSavedEventIds(context)
//    eventViewModel.fetchEvents()

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Event.route
    ) {
        composable(
            route = BottomBarScreen.Event.route
        ) {
            EventScreen(navController, eventViewModel,postViewModel)
        }
        composable(
            route = BottomBarScreen.Profile.route
        ) {
            ProfileScreen(navController, profileViewModel, postViewModel) { postId ->
                navController.navigate("post_detail/$postId")
            }
        }
        composable(
            route = Graph.EVENT_DETAILS
        ) {
//            val event = navController.currentBackStackEntry?.savedStateHandle?.get<Event>("event")
            EventDetailsScreen(navController, eventViewModel, startActivity)
        }
        composable(
            route = Graph.PROFILE
        ) {
            ProfileScreen(navController, profileViewModel, postViewModel) { postId ->
                navController.navigate("post_detail/$postId")
            }
        }
//        composable(
//            route = Graph.REPORT
//        ) {
//            ReportScreen(navController)
//        }
        composable(route = Graph.REPORT) {navBackStackEntry ->
            val postID = navBackStackEntry.arguments?.getString("docId")
            if (postID != null) {
                ReportScreen(postID, navController, postViewModel::onReportSubmit)
            } else {
                navController.navigateUp()
            }
        }
//        postNavGraph(navController)
        composable(BottomBarScreen.Post.route) {
            CommunityScreen(navController, postViewModel) { postId ->
                navController.navigate("post_detail/$postId")
            }
        }
        composable(Graph.POST_DETAILS) { navBackStackEntry ->
            val postID = navBackStackEntry.arguments?.getString("postID")
            if (postID != null) {
                PostDetailsScreen(postID = postID, navController = navController, postViewModel)
            } else {
                navController.navigateUp()
            }
        }
        composable(Graph.POST_CREATE) { navBackStackEntry ->
            PostCreateScreen(navController = navController, postViewModel)
        }
        composable(route = Graph.POST_INFO){
            PostUI(navController=navController, postViewModel = postViewModel)
        }
    }
}



