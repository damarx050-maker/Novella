package com.novella.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.novella.app.data.repo.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val settings: SettingsRepository) : ViewModel() {
    private val _shown = MutableStateFlow(settings.onboardingShown)
    val shown: StateFlow<Boolean> = _shown

    fun setShown() {
        settings.onboardingShown = true
        _shown.value = true
    }
}
