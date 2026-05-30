package com.example.sampleapp.ui.screens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.data.entity.UserEntity
import com.example.sampleapp.ui.AppViewModel
import com.example.sampleapp.ui.components.DetailBottomSheet
import com.example.sampleapp.ui.components.DetailScreen
import com.example.sampleapp.ui.components.MockCatalog

private data class ProfileItem(val label: String, val icon: ImageVector, val tag: String)

private val profileSections = listOf(
    ProfileItem("Edit Profile", Icons.Filled.Edit, "profile_edit"),
    ProfileItem("Security", Icons.Filled.Lock, "profile_security"),
    ProfileItem("Privacy", Icons.Filled.Shield, "profile_privacy"),
    ProfileItem("Notifications", Icons.Filled.Notifications, "profile_notifications"),
    ProfileItem("Help Center", Icons.AutoMirrored.Filled.HelpOutline, "profile_help")
)

@Composable
fun ProfileTab(
    viewModel: AppViewModel,
    user: UserEntity?,
    onLogout: () -> Unit,
    onMessage: (String) -> Unit = {}
) {
    var detail by remember { mutableStateOf<DetailScreen?>(null) }
    var showEdit by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("profile_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer) {
            Box(modifier = Modifier.size(96.dp), contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Profile image",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(56.dp)
                        .testTag("profile_image")
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(user?.name ?: "Guest User", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
            Icon(Icons.Filled.Star, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(16.dp))
            Spacer(Modifier.size(4.dp))
            Text(
                "${user?.membershipLevel ?: "Standard"} Member",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(20.dp))

        Card(modifier = Modifier
            .fillMaxWidth()
            .testTag("profile_info_card")) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(Icons.Filled.Email, "Email", user?.email ?: "guest@example.com")
                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                InfoRow(Icons.Filled.Phone, "Phone", user?.phone ?: "Not provided")
                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                InfoRow(Icons.Filled.Person, "Username", user?.username ?: "Not set")
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column {
                profileSections.forEach { item ->
                    SettingRow(item.label, item.icon, item.tag) {
                        when (item.label) {
                            "Edit Profile" -> showEdit = true
                            "Security" -> detail = MockCatalog.securitySettings()
                            "Privacy" -> detail = MockCatalog.privacySettings()
                            "Notifications" -> detail = MockCatalog.notificationSettings()
                            "Help Center" -> detail = MockCatalog.helpCenter()
                        }
                    }
                }
                SettingRow("Logout", Icons.AutoMirrored.Filled.Logout, "profile_logout", onClick = onLogout)
            }
        }
        Spacer(Modifier.height(24.dp))
    }

    detail?.let {
        DetailBottomSheet(
            detail = it,
            onDismiss = { detail = null },
            onEntryClick = { entry -> onMessage("Opened: ${entry.title}") }
        )
    }

    if (showEdit) {
        EditProfileSheet(
            viewModel = viewModel,
            user = user,
            onDismiss = { showEdit = false },
            onSaved = {
                showEdit = false
                onMessage("Profile updated")
            }
        )
    }
}

@Composable
private fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.size(14.dp))
        Column {
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun SettingRow(label: String, icon: ImageVector, tag: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
            .testTag(tag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.size(16.dp))
        Text(label, fontSize = 15.sp, modifier = Modifier.weight(1f))
    }
}
