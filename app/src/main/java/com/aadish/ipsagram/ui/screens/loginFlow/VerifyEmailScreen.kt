package com.aadish.ipsagram.ui.screens.loginFlow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.aadish.ipsagram.util.Utils.Companion.showMessage
import com.aadish.ipsagram.data.model.Response.Loading
import com.aadish.ipsagram.data.model.Response.Success
import com.aadish.ipsagram.data.model.Response.Failure
import com.aadish.ipsagram.ui.navigation.Graph
import com.aadish.ipsagram.ui.theme.darkBackground
import com.aadish.ipsagram.ui.theme.white
import com.aadish.ipsagram.ui.viewModels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyEmailScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
//    navigateToHomeScreen: () -> Unit
) {
//    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Verify Email",
                    modifier = Modifier
                        .padding(vertical = 20.dp),
                    style = TextStyle(
                        color = white,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    ),
                    textAlign = TextAlign.Start
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 32.dp, end = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.clickable {
                        viewModel.reloadUser()
                    },
                    text = "Already verified?",
                    color = white,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
                Text(
                    text = "If not, check spam",
                    color = white,
                    fontSize = 15.sp
                )
            }

            // reload user
            when(val reloadUserResponse = viewModel.reloadUserResponse) {
                is Loading -> CircularProgressIndicator()
                is Success -> {
                    val isUserReloaded = reloadUserResponse.data
                    LaunchedEffect(isUserReloaded) {
                        if (isUserReloaded) {
                            if (viewModel.isEmailVerified) {
                                navController.navigate(Graph.HOME) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                showMessage(context, "Email is not verified yet")
                            }
                        }
                    }
                }
                is Failure -> reloadUserResponse.apply {
                    LaunchedEffect(e) {
                        print(e)
                    }
                }
            }
        }
    }
}