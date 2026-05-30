package com.example.sampleapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sampleapp.data.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Insert
    suspend fun insertAll(activities: List<ActivityEntity>)

    @Insert
    suspend fun insert(activity: ActivityEntity): Long

    @Query("SELECT * FROM activities ORDER BY id DESC")
    fun observeAll(): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE category = :category ORDER BY id DESC")
    fun observeByCategory(category: String): Flow<List<ActivityEntity>>

    @Query("SELECT COUNT(*) FROM activities")
    suspend fun count(): Int
}
