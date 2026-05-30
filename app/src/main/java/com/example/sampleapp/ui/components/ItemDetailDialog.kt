package com.example.sampleapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.data.entity.ActivityEntity

@Composable
fun ItemDetailDialog(
    activity: ActivityEntity,
    onDismiss: () -> Unit
) {
    val color = colorFor(activity.category)
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("item_detail_dialog"),
        icon = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(iconFor(activity.category), contentDescription = null, tint = color, modifier = Modifier.size(26.dp))
            }
        },
        title = { Text(activity.title, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                DetailLine("Category", activity.category)
                DetailLine("Description", activity.subtitle)
                if (activity.amount.isNotBlank()) DetailLine("Amount", activity.amount)
                DetailLine("Time", activity.timestamp)
                DetailLine("Reference", "#${100000 + activity.id}")
                DetailLine("Status", if (activity.read) "Read" else "Unread")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.testTag("item_detail_close")) {
                Text("Close")
            }
        }
    )
}

@Composable
private fun DetailLine(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.size(12.dp))
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium)
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
