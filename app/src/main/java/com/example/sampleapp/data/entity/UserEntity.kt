package com.example.sampleapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Local user account. Stored entirely on-device via Room — no network involved.
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    // Optional profile details captured during the setup screen.
    val username: String? = null,
    val phone: String? = null,
    val city: String? = null,
    val membershipLevel: String = "Standard",
    val acceptedTerms: Boolean = false
)
