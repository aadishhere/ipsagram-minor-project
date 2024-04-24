package com.aadish.ipsagram

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aadish.ipsagram.ui.navigation.Graph
import com.aadish.ipsagram.ui.navigation.RootNavigationGraph
import com.aadish.ipsagram.ui.screens.HomeScreen
import com.aadish.ipsagram.ui.theme.FinalProjectTheme
import com.aadish.ipsagram.ui.viewModels.AuthViewModel
import com.aadish.ipsagram.ui.viewModels.MainViewModel
import com.aadish.ipsagram.ui.viewModels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


//            val postsVM = PostsViewModel(AppModule.provideStorageService())
//            LaunchedEffect(postsVM) {
//                val postList = postsVM.posts.onEach {
//                    for( post in it) {
//                        Log.d(TAG, "posts: $post")
//                    }
//                }
//            }
            val postViewModel =  hiltViewModel<PostViewModel>()
            navController = rememberNavController()
            FinalProjectTheme(darkTheme = true) {
                RootNavigationGraph(navController, this::startActivity,postViewModel)

            }
            AuthState(this::startActivity,postViewModel)

        }

    }

    @SuppressLint("CommitPrefEdits", "CoroutineCreationDuringComposition")
    @Composable
    private fun AuthState(startActivity: (Intent) -> Unit,postViewModel: PostViewModel) {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            if (viewModel.isEmailVerified) {

                Log.d(TAG, "useID: ${viewModel.userID}")
                HomeScreen(startActivity = startActivity, postViewModel = postViewModel)
            } else {
                NavigateToVerifyEmailScreen()
            }
        }
    }

    @Composable
    private fun NavigateToSignInScreen() = navController.navigate(Graph.AUTHENTICATION) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToHomeScreen() = navController.navigate(Graph.HOME) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToVerifyEmailScreen() = navController.navigate(Graph.VERIFY_EMAIL) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinalProjectTheme {
//        Greeting("Android")
    }
}