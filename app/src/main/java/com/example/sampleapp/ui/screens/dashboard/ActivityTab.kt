package com.example.sampleapp.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sampleapp.data.entity.ActivityEntity
import com.example.sampleapp.ui.AppViewModel
import com.example.sampleapp.ui.components.ItemDetailDialog

private val filters = listOf("All", "Activity", "Notification", "Transaction", "Order")

@Composable
fun ActivityTab(viewModel: AppViewModel) {
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedActivity by remember { mutableStateOf<ActivityEntity?>(null) }
    val activities by viewModel.observeActivities().collectAsStateWithLifecycle(initialValue = emptyList())

    val filtered = if (selectedFilter == "All") activities
    else activities.filter { it.category == selectedFilter }

    Column(modifier = Modifier
        .fillMaxSize()
        .testTag("activity_screen")) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    modifier = Modifier.testTag("filter_${filter.lowercase()}")
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("activity_list"),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filtered, key = { it.id }) { activity ->
                ActivityRow(activity) { selectedActivity = activity }
            }
        }
    }

    selectedActivity?.let {
        ItemDetailDialog(activity = it, onDismiss = { selectedActivity = null })
    }
}

@Composable
private fun ActivityRow(activity: ActivityEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("activity_item_${activity.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val color = colorFor(activity.category)
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(color.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(iconFor(activity.category), contentDescription = activity.category, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(activity.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, maxLines = 1)
                Text("${activity.category} • ${activity.subtitle}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
            }
            Column(horizontalAlignment = Alignment.End) {
                if (activity.amount.isNotBlank()) {
                    Text(activity.amount, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Text(activity.timestamp, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

private fun colorFor(category: String): Color = when (category) {
    "Transaction" -> Color(0xFF2E7D32)
    "Notification" -> Color(0xFFF9A825)
    "Order" -> Color(0xFF1565C0)
    else -> Color(0xFF6650A4)
}

private fun iconFor(category: String): ImageVector = when (category) {
    "Transaction" -> Icons.Filled.Payment
    "Notification" -> Icons.Filled.Notifications
    "Order" -> Icons.Filled.ShoppingCart
    else -> Icons.Filled.History
}
