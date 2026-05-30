package com.example.sampleapp.ui.screens.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Badge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.ui.AppViewModel
import kotlinx.coroutines.launch

private data class TabItem(val label: String, val icon: ImageVector, val tag: String)

private val TABS = listOf(
    TabItem("Home", Icons.Filled.Home, "nav_home"),
    TabItem("Activity", Icons.AutoMirrored.Filled.List, "nav_activity"),
    TabItem("Profile", Icons.Filled.Person, "nav_profile"),
    TabItem("Settings", Icons.Filled.Settings, "nav_settings")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: AppViewModel,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showFabDialog by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val onMessage: (String) -> Unit = { msg ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(msg)
        }
    }

    val userId by viewModel.currentUserId.collectAsState()
    val user by viewModel.observeUser(userId ?: -1L).let { flow ->
        flow.collectAsState(initial = null)
    }
    val displayName = user?.name ?: "Guest User"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.testTag("nav_drawer")) {
                Text(
                    text = "SampleApk",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(20.dp)
                )
                TABS.forEachIndexed { index, tab ->
                    NavigationDrawerItem(
                        label = { Text(tab.label) },
                        icon = { Icon(tab.icon, contentDescription = null) },
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .testTag("drawer_${tab.tag}")
                    )
                }
                NavigationDrawerItem(
                    label = { Text("Logout") },
                    icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    selected = false,
                    onClick = onLogout,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .testTag("drawer_logout")
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.testTag("dashboard_screen"),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text(TABS[selectedTab].label) },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } },
                            modifier = Modifier.testTag("btn_open_drawer")
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "Open navigation drawer")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { selectedTab = 1 },
                            modifier = Modifier.testTag("btn_notifications")
                        ) {
                            BadgedBox(badge = { Badge { Text("5") } }) {
                                Icon(
                                    Icons.Filled.Notifications,
                                    contentDescription = "Notifications"
                                )
                            }
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(modifier = Modifier.testTag("bottom_nav")) {
                    TABS.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) },
                            modifier = Modifier.testTag(tab.tag)
                        )
                    }
                }
            },
            floatingActionButton = {
                if (selectedTab == 0) {
                    FloatingActionButton(
                        onClick = { showFabDialog = true },
                        modifier = Modifier.testTag("fab_create")
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Create")
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    0 -> HomeTab(viewModel = viewModel, displayName = displayName, onMessage = onMessage)
                    1 -> ActivityTab(viewModel = viewModel)
                    2 -> ProfileTab(viewModel = viewModel, user = user, onLogout = onLogout, onMessage = onMessage)
                    3 -> SettingsTab(viewModel = viewModel)
                }
            }
        }
    }

    if (showFabDialog) {
        CreateActionDialog(
            onDismiss = { showFabDialog = false },
            onCreate = { action ->
                when (action) {
                    "Create Task" -> viewModel.addActivity(
                        "New task created", "Added from Quick Create", "Activity"
                    )
                    "Add Note" -> viewModel.addActivity(
                        "Note added", "Saved to your notes", "Activity"
                    )
                    "Upload File" -> viewModel.addActivity(
                        "File uploaded", "document.pdf · 1.2 MB", "Activity"
                    )
                }
                showFabDialog = false
                selectedTab = 0
                onMessage("$action — done")
            }
        )
    }
}
