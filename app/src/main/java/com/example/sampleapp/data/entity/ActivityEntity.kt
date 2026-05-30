package com.example.sampleapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A dummy activity / transaction / notification entry rendered in the dashboard feed.
 */
@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val subtitle: String,
    val category: String, // Activity | Notification | Transaction | Order
    val amount: String,
    val timestamp: String,
    val read: Boolean = false
)
