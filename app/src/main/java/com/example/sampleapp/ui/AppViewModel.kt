package com.example.sampleapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.SampleApplication
import com.example.sampleapp.data.entity.ActivityEntity
import com.example.sampleapp.data.entity.PreferencesEntity
import com.example.sampleapp.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Shared session + preferences state for the whole app. Backed entirely by Room.
 */
class AppViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = (app as SampleApplication).repository

    private val _currentUserId = MutableStateFlow<Long?>(null)
    val currentUserId: StateFlow<Long?> = _currentUserId.asStateFlow()

    val preferences: StateFlow<PreferencesEntity> =
        repository.observePreferences()
            .map { it ?: PreferencesEntity() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PreferencesEntity())

    fun observeUser(id: Long): Flow<UserEntity?> = repository.observeUser(id)

    fun observeActivities(): Flow<List<ActivityEntity>> = repository.observeActivities()

    fun observeActivities(category: String): Flow<List<ActivityEntity>> =
        repository.observeActivities(category)

    // ---- Auth ----
    fun login(email: String, password: String, onResult: (Result<Long>) -> Unit) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            result.onSuccess { _currentUserId.value = it.id }
            onResult(result.map { it.id })
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        onResult: (Result<Long>) -> Unit
    ) {
        viewModelScope.launch {
            val result = repository.register(name, email, password)
            result.onSuccess { _currentUserId.value = it }
            onResult(result)
        }
    }

    // ---- Terms ----
    fun acceptTerms(onDone: () -> Unit) {
        val id = _currentUserId.value ?: return
        viewModelScope.launch {
            repository.getUser(id)?.let {
                repository.updateUser(it.copy(acceptedTerms = true))
            }
            onDone()
        }
    }

    // ---- Setup ----
    fun saveSetup(
        username: String,
        phone: String,
        city: String,
        onDone: () -> Unit
    ) {
        val id = _currentUserId.value ?: return
        viewModelScope.launch {
            repository.getUser(id)?.let {
                repository.updateUser(
                    it.copy(
                        username = username.ifBlank { null },
                        phone = phone.ifBlank { null },
                        city = city.ifBlank { null }
                    )
                )
            }
            onDone()
        }
    }

    fun updateProfile(
        name: String,
        username: String,
        phone: String,
        city: String,
        membershipLevel: String,
        onDone: () -> Unit
    ) {
        val id = _currentUserId.value ?: return
        viewModelScope.launch {
            repository.getUser(id)?.let {
                repository.updateUser(
                    it.copy(
                        name = name.ifBlank { it.name },
                        username = username.ifBlank { null },
                        phone = phone.ifBlank { null },
                        city = city.ifBlank { null },
                        membershipLevel = membershipLevel.ifBlank { it.membershipLevel }
                    )
                )
            }
            onDone()
        }
    }

    // ---- Activities ----
    fun addActivity(title: String, subtitle: String, category: String, amount: String = "") {
        viewModelScope.launch {
            val time = java.text.SimpleDateFormat("HH:mm", java.util.Locale.US)
                .format(java.util.Date())
            repository.addActivity(
                ActivityEntity(
                    title = title,
                    subtitle = subtitle,
                    category = category,
                    amount = amount,
                    timestamp = time
                )
            )
        }
    }

    // ---- Preferences ----
    fun setDarkMode(enabled: Boolean) = updatePrefs { it.copy(darkMode = enabled) }
    fun setNotifications(enabled: Boolean) = updatePrefs { it.copy(notificationsEnabled = enabled) }
    fun setLanguage(language: String) = updatePrefs { it.copy(language = language) }

    private fun updatePrefs(transform: (PreferencesEntity) -> PreferencesEntity) {
        viewModelScope.launch {
            val current = repository.getPreferences()
            repository.savePreferences(transform(current))
        }
    }

    fun logout() {
        _currentUserId.value = null
    }
}
