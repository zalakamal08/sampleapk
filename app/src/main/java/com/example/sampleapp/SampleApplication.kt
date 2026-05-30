package com.example.sampleapp

import android.app.Application
import com.example.sampleapp.data.AppDatabase
import com.example.sampleapp.data.SampleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Application entry point. Owns the database/repository and seeds dummy data on
 * first launch. No dependency-injection framework is used to keep the sample simple.
 */
class SampleApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val repository: SampleRepository by lazy { SampleRepository(database) }

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        appScope.launch {
            repository.seedIfNeeded()
        }
    }
}
