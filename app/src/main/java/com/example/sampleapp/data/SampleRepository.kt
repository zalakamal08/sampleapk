package com.example.sampleapp.data

import com.example.sampleapp.data.entity.ActivityEntity
import com.example.sampleapp.data.entity.PreferencesEntity
import com.example.sampleapp.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Single point of access to the local Room database. Everything is offline.
 */
class SampleRepository(private val db: AppDatabase) {

    private val userDao = db.userDao()
    private val prefsDao = db.preferencesDao()
    private val activityDao = db.activityDao()

    // ---- Users ----
    suspend fun register(name: String, email: String, password: String): Result<Long> {
        val existing = userDao.findByEmail(email)
        if (existing != null) {
            return Result.failure(IllegalStateException("An account with this email already exists"))
        }
        val id = userDao.insert(UserEntity(name = name, email = email, password = password))
        return if (id > 0) Result.success(id) else Result.failure(IllegalStateException("Could not create account"))
    }

    suspend fun login(email: String, password: String): Result<UserEntity> {
        val user = userDao.authenticate(email, password)
        return if (user != null) Result.success(user)
        else Result.failure(IllegalStateException("Invalid email or password"))
    }

    suspend fun findByEmail(email: String): UserEntity? = userDao.findByEmail(email)

    suspend fun getUser(id: Long): UserEntity? = userDao.getById(id)

    fun observeUser(id: Long): Flow<UserEntity?> = userDao.observeUser(id)

    suspend fun updateUser(user: UserEntity) = userDao.update(user)

    // ---- Preferences ----
    fun observePreferences(): Flow<PreferencesEntity?> = prefsDao.observe()

    suspend fun getPreferences(): PreferencesEntity = prefsDao.get() ?: PreferencesEntity()

    suspend fun savePreferences(prefs: PreferencesEntity) = prefsDao.upsert(prefs)

    // ---- Activities ----
    fun observeActivities(): Flow<List<ActivityEntity>> = activityDao.observeAll()

    fun observeActivities(category: String): Flow<List<ActivityEntity>> =
        activityDao.observeByCategory(category)

    suspend fun addActivity(activity: ActivityEntity): Long = activityDao.insert(activity)

    /**
     * Seeds preferences + ~54 dummy activities the first time the app runs.
     */
    suspend fun seedIfNeeded() {
        if (prefsDao.get() == null) {
            prefsDao.upsert(PreferencesEntity())
        }
        if (userDao.count() == 0) {
            DummyData.users().forEach { userDao.insert(it) }
        }
        if (activityDao.count() == 0) {
            activityDao.insertAll(DummyData.activities())
        }
    }
}
