package com.example.sampleapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sampleapp.data.dao.ActivityDao
import com.example.sampleapp.data.dao.PreferencesDao
import com.example.sampleapp.data.dao.UserDao
import com.example.sampleapp.data.entity.ActivityEntity
import com.example.sampleapp.data.entity.PreferencesEntity
import com.example.sampleapp.data.entity.UserEntity

@Database(
    entities = [UserEntity::class, PreferencesEntity::class, ActivityEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun preferencesDao(): PreferencesDao
    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sampleapk.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
