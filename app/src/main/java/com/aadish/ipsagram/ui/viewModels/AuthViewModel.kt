package com.aadish.ipsagram.ui.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aadish.ipsagram.data.service.AuthService
import com.aadish.ipsagram.data.model.Response
import com.aadish.ipsagram.data.model.Response.Success
import com.aadish.ipsagram.data.model.Response.Loading
import com.aadish.ipsagram.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val service: AuthService
): ViewModel() {
    var signInResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String, context: Context) = viewModelScope.launch {
        signInResponse = Loading
        signInResponse = service.firebaseSignInWithEmailAndPassword(email, password)

        if (signInResponse is Response.Failure) {
            Utils.showMessage(context, "Wrong email or password. Please try again.")
        } else if (signInResponse is Success) {
            Utils.showMessage(context, "Login success")
        }
    }
}