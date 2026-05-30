package com.example.sampleapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapp.data.entity.PreferencesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferencesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(prefs: PreferencesEntity)

    @Query("SELECT * FROM preferences WHERE id = 1 LIMIT 1")
    fun observe(): Flow<PreferencesEntity?>

    @Query("SELECT * FROM preferences WHERE id = 1 LIMIT 1")
    suspend fun get(): PreferencesEntity?
}
