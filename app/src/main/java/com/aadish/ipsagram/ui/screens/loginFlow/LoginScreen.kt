package com.aadish.ipsagram.ui.screens.loginFlow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.aadish.ipsagram.ui.navigation.Graph
import com.aadish.ipsagram.ui.theme.darkBackground
import com.aadish.ipsagram.ui.theme.green
import com.aadish.ipsagram.ui.theme.grey
import com.aadish.ipsagram.ui.theme.white
import com.aadish.ipsagram.ui.viewModels.AuthViewModel


@Composable
fun LoginScreen(

    navController: NavHostController,
    navigateToForgotPasswordScreen: () -> Unit,
//    navigateToSignUpScreen: () -> Unit,
    viewModel: AuthViewModel= hiltViewModel(),
) {
    var isLoginFilled by remember { mutableStateOf(false) }
    var isPasswordFilled by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

//    val loginFlow = viewModel?.loginFlow?.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(20.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Login",
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

            // Email Text Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(
                    text = "Email",
                    color = grey
                ) },
                value = email,
                keyboardOptions = KeyboardOptions.Default,
                onValueChange = {
                    email = it
                    isLoginFilled = it.isNotEmpty()
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = white,
                    focusedContainerColor = darkBackground,
                    focusedIndicatorColor = grey,
                    unfocusedTextColor = white,
                    unfocusedContainerColor = darkBackground,
                    unfocusedIndicatorColor = grey,
                    cursorColor = grey
                )
            )

            // Password Text Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(
                    text = "Password",
                    color = grey
                ) },
                value = password,
                keyboardOptions = KeyboardOptions.Default,
                onValueChange = {
                    password = it
                    isPasswordFilled = it.isNotEmpty()
                },
                singleLine = true,
                trailingIcon = {
                    val iconImage = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    val description = if (passwordVisible) {
                        "Hide password"
                    } else {
                        "Show password"
                    }

                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(imageVector = iconImage, contentDescription = description)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = white,
                    focusedContainerColor = darkBackground,
                    focusedIndicatorColor = grey,
                    unfocusedTextColor = white,
                    unfocusedContainerColor = darkBackground,
                    unfocusedIndicatorColor = grey,
                    cursorColor = grey
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(Graph.SIGN_UP) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = grey),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "Do not have an account? Sign Up!",
                        style = TextStyle(
//                            fontFamily = FontFamily.Default,
                            color = grey,
                            fontSize = 14.sp
                        )
                    )
                }

//                Text(
//                    text = "|",
//                    style = TextStyle(
//                        color = grey
//                    ),
//                    modifier = Modifier.padding(horizontal = 8.dp)
//                )
//
//                TextButton(
//                    onClick = navigateToForgotPasswordScreen,
//                    colors = ButtonDefaults.textButtonColors(
//                        contentColor = grey
//                    )
//                ) {
//                    Text(
//                        text = "Forgot Password",
//                        style = TextStyle(
////                            fontFamily = FontFamily.Default,
//                            color = grey,
//                            fontSize = 14.sp
//                        )
//                    )
//                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedButton(
                onClick = {
                    keyboardController?.hide()
                    viewModel.signInWithEmailAndPassword(email, password, context)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    if(isLoginFilled && isPasswordFilled) green else grey
                ),
                shape = RoundedCornerShape(20)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Sign In",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

//            loginFlow?.value?.let {
//                when (it) {
//                    is Resource.Failure -> {
//                        val context = LocalContext.current
//                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
//                    }
//                    is Resource.loading -> {
//                        CircularProgressIndicator()
//                    }
//                    is Resource.Success -> {
//                        LaunchedEffect(Unit) {
//                            navController.navigate(Graph.HOME) {
//                                popUpTo(Graph.AUTHENTICATION) { inclusive = true }
//                            }
//                        }
//                    }
//                }
//            }

        }
    }
}

//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(navigateToForgotPasswordScreen = { Unit }, navigateToSignUpScreen = { Unit })
//}



