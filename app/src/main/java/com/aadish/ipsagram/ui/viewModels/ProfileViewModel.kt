package com.aadish.ipsagram.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aadish.ipsagram.data.service.AuthService
import com.aadish.ipsagram.data.model.Response
import com.aadish.ipsagram.data.model.Response.Success
import com.aadish.ipsagram.data.model.Response.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AuthService
): ViewModel() {
    var revokeAccessResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    var reloadUserResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set

    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = Loading
        reloadUserResponse = repo.reloadFirebaseUser()
    }

    val isEmailVerified get() = repo.currentUser?.isEmailVerified ?: false
//    val isEmailVerified get() = true
    val getUserId get() = repo.currentUser?.uid

    fun signOut() = repo.signOut()

    fun revokeAccess() = viewModelScope.launch {
        revokeAccessResponse = Loading
        revokeAccessResponse = repo.revokeAccess()
    }
}