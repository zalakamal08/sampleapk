package com.example.sampleapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Single-row table (id is always 1) holding app-wide preferences.
 */
@Entity(tableName = "preferences")
data class PreferencesEntity(
    @PrimaryKey
    val id: Int = 1,
    val darkMode: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val language: String = "English"
)
