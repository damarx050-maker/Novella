package com.novella.app.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.novella.app.data.repo.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(app: Application, private val settings: SettingsRepository) : AndroidViewModel(app) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null || settings.isLoggedIn)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun setLoggedIn(userId: String) {
        viewModelScope.launch {
            settings.isLoggedIn = true
            settings.userId = userId
            _isLoggedIn.value = true
        }
    }
}
