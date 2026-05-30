package com.example.sampleapp.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sampleapp.ui.AppViewModel

private val languages = listOf("English", "Spanish", "French", "German", "Hindi", "Arabic")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTab(viewModel: AppViewModel) {
    val prefs by viewModel.preferences.collectAsStateWithLifecycle()
    var languageExpanded by remember { mutableStateOf(false) }
    var showAbout by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("settings_screen")
    ) {
        Text("Preferences", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                ToggleRow(
                    icon = Icons.Filled.DarkMode,
                    label = "Dark Mode",
                    checked = prefs.darkMode,
                    tag = "toggle_dark_mode",
                    onCheckedChange = { viewModel.setDarkMode(it) }
                )
                ToggleRow(
                    icon = Icons.Filled.Notifications,
                    label = "Notifications",
                    checked = prefs.notificationsEnabled,
                    tag = "toggle_notifications",
                    onCheckedChange = { viewModel.setNotifications(it) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Language", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = languageExpanded,
            onExpandedChange = { languageExpanded = it },
            modifier = Modifier.testTag("language_dropdown")
        ) {
            OutlinedTextField(
                value = prefs.language,
                onValueChange = {},
                readOnly = true,
                label = { Text("Language") },
                leadingIcon = { Icon(Icons.Filled.Language, contentDescription = null) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = languageExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = languageExpanded,
                onDismissRequest = { languageExpanded = false }
            ) {
                languages.forEach { lang ->
                    DropdownMenuItem(
                        text = { Text(lang) },
                        onClick = {
                            viewModel.setLanguage(lang)
                            languageExpanded = false
                        },
                        modifier = Modifier.testTag("lang_${lang.lowercase()}")
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("About", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable { showAbout = true }
            .testTag("about_app")) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("SampleApk", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    Text(
                        "Version 1.0.0 — an offline sample app for mobile UI automation testing.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }

    if (showAbout) {
        AlertDialog(
            onDismissRequest = { showAbout = false },
            modifier = Modifier.testTag("about_dialog"),
            icon = { Icon(Icons.Filled.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            title = { Text("About SampleApk") },
            text = {
                Column {
                    AboutLine("Version", "1.0.0")
                    AboutLine("Build", "Offline / Debug-Release")
                    AboutLine("Package", "com.example.sampleapp")
                    AboutLine("Database", "Room (SQLite) · local")
                    AboutLine("UI", "Jetpack Compose · Material 3")
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "A sample application for mobile UI automation, accessibility, " +
                            "gesture, and agent testing. No internet required.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showAbout = false }, modifier = Modifier.testTag("about_close")) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun AboutLine(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.width(12.dp))
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ToggleRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    checked: Boolean,
    tag: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(16.dp))
        Text(label, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.testTag(tag)
        )
    }
}
