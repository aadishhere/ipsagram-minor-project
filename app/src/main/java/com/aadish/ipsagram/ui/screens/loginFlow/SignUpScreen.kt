package com.aadish.ipsagram.ui.screens.loginFlow

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aadish.ipsagram.util.Utils.Companion.showMessage
import com.aadish.ipsagram.data.model.Response.Loading
import com.aadish.ipsagram.data.model.Response.Success
import com.aadish.ipsagram.data.model.Response.Failure
import com.aadish.ipsagram.ui.theme.darkBackground
import com.aadish.ipsagram.ui.theme.green
import com.aadish.ipsagram.ui.theme.grey
import com.aadish.ipsagram.ui.theme.red
import com.aadish.ipsagram.ui.theme.white
import com.aadish.ipsagram.ui.viewModels.SignUpViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val textFieldColours = TextFieldDefaults.colors(
        focusedTextColor = white,
        focusedContainerColor = darkBackground,
        focusedIndicatorColor = grey,
        unfocusedTextColor = white,
        unfocusedContainerColor = darkBackground,
        unfocusedIndicatorColor = grey,
        cursorColor = grey
    )

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isValidLength by remember { mutableStateOf(false) }

    var isNameFilled by remember { mutableStateOf(false) }
    var isEmailFilled by remember { mutableStateOf(false) }
    var isPasswordFilled by remember { mutableStateOf(false) }
    var isConfirmPasswordFilled by remember { mutableStateOf(false) }
//    var isEmailFormatCorrect by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current


    Scaffold(
        modifier = Modifier
//            .fillMaxSize()
            .background(darkBackground),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = darkBackground),
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
            )
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sign Up",
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

//            TextFieldComponent(label = "Name", value = name, onTextChanged = { isNameFilled = it.isNotEmpty() })
//            TextFieldComponent(label = "Email", value = email, onTextChanged = { isEmailFilled = it.isNotEmpty() })

            // Username
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(
                    text = "Username",
                    color = grey
                ) },
                value = name,
                keyboardOptions = KeyboardOptions.Default,
                onValueChange = {
                    name = it
                    isNameFilled = it.isNotEmpty()
                },
                singleLine = true,
                colors = textFieldColours
            )

            // Email
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
                    isEmailFilled = it.isNotEmpty()
//                    isEmailFormatCorrect = it.endsWith("@connect.ust.hk")
                },
                singleLine = true,
                colors = textFieldColours
            )

//            if(!isEmailFormatCorrect && isEmailFilled) {
//                Text(
//                    text = "Please enter a valid IPSA email address",
//                    color = red,
//                    modifier = Modifier
//                        .padding(start = 12.dp, bottom = 3.dp, top = 2.dp),
//                    fontSize = 14.sp
//                )
//            }

            // Password
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(
                    text = "Enter your password",
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
                colors = textFieldColours
            )

            if (password.length in 1..6) {
                isValidLength = false
                Text(
                    text = "Password should be more than 6 characters",
                    color = red,
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 3.dp, top = 2.dp),
                    fontSize = 14.sp
                )
            } else {
                isValidLength = true
            }

            // Confirm Password
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(
                    text = "Confirm your password",
                    color = grey
                ) },
                value = confirmPassword,
                keyboardOptions = KeyboardOptions.Default,
                onValueChange = {
                    confirmPassword = it
                    isConfirmPasswordFilled = it.isNotEmpty()
                },
                singleLine = true,
                trailingIcon = {
                    val iconImage = if (confirmPasswordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    val description = if (confirmPasswordVisible) {
                        "Hide password"
                    } else {
                        "Show password"
                    }

                    IconButton(onClick = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }) {
                        Icon(imageVector = iconImage, contentDescription = description)
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = textFieldColours
            )

            if (password != confirmPassword) {
                isPasswordValid = false
                Text(
                    text = "Passwords do not match",
                    color = red,
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 3.dp, top = 2.dp),
                    fontSize = 14.sp
                )
            } else {
                isPasswordValid = true
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = {
                    keyboard?.hide()
                    if(isNameFilled && isEmailFilled && isPasswordFilled && isConfirmPasswordFilled
                        && isPasswordValid && isValidLength /* && isEmailFormatCorrect  */) {
                        viewModel.signUpWithEmailAndPassword(email, password, name)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    if(isNameFilled && isEmailFilled && isPasswordFilled && isConfirmPasswordFilled
                         && isPasswordValid && isValidLength /* && isEmailFormatCorrect */ ) green else grey
                ),
                shape = RoundedCornerShape(20)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            SignUp(
                sendEmailVerification = {
                    viewModel.sendEmailVerification()
                },
                showVerifyEmailMessage = {
                    showMessage(context, "We've sent you an email with a link to verify the email.")
                }
            )

            // Send Email Verification
            when(val sendEmailVerificationResponse = viewModel.sendEmailVerificationResponse) {
                is Loading -> CircularProgressIndicator()
                is Success -> Unit
                is Failure -> sendEmailVerificationResponse.apply {
                    LaunchedEffect(e) {
                        print(e)
                    }
                }
            }
                
        }
    }

}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}