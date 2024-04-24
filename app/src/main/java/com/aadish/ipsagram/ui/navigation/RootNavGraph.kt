package com.aadish.ipsagram.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aadish.ipsagram.ui.screens.HomeScreen
import com.aadish.ipsagram.ui.screens.PostCreateScreen
import com.aadish.ipsagram.ui.screens.PostUI
import com.aadish.ipsagram.ui.screens.loginFlow.LoginScreen
import com.aadish.ipsagram.ui.screens.loginFlow.SignUpScreen
import com.aadish.ipsagram.ui.screens.loginFlow.VerifyEmailScreen
import com.aadish.ipsagram.ui.viewModels.AuthViewModel
import com.aadish.ipsagram.ui.viewModels.PostViewModel

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    startActivity: (Intent) -> Unit,
   viewModel: PostViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        composable(route = Graph.AUTHENTICATION) {
            LoginScreen(
                navController = navController,
                navigateToForgotPasswordScreen = {
//                    navController.navigate(ForgotPasswordScreen.route)
                },


            )
        }
        composable(route = Graph.HOME) {
            HomeScreen(startActivity=startActivity, postViewModel = viewModel)
        }
        composable(route = Graph.SIGN_UP) {
            SignUpScreen(navController = navController)
        }
        composable(route = Graph.VERIFY_EMAIL) {
            VerifyEmailScreen(
                navController = navController,
//                navigateToHomeScreen = {
//
//                }
            )
        }
        composable(route = Graph.POST_CREATE){
            PostCreateScreen(navController = navController,viewModel)
        }
        composable(route = Graph.POST_INFO){
            PostUI(navController = navController, postViewModel = viewModel)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "event_graph"
    const val EVENT_DETAILS = "event_detail_graph/{event}"
    const val POST_DETAILS = "post_detail/{postID}"
    const val POST_CREATE = "post_create"
    const val PROFILE = "profile_graph"
    const val POST = "post"
    const val COMMUNITY = "community"
    const val REPORT = "report_graph/{docId}"
    const val VERIFY_EMAIL = "verify_email"
    const val SIGN_UP = "sign_up"
    const val POST_INFO = "postInfo"
}