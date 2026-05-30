package com.example.sampleapp.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

private data class CarouselCard(val title: String, val subtitle: String, val icon: ImageVector, val color: Color)
private data class Stat(val label: String, val value: String, val icon: ImageVector, val color: Color)
private data class QuickAction(val label: String, val icon: ImageVector, val tag: String)

private val carouselCards = listOf(
    CarouselCard("Analytics", "View insights", Icons.Filled.Analytics, Color(0xFF1565C0)),
    CarouselCard("Reports", "Monthly reports", Icons.Filled.Receipt, Color(0xFF2E7D32)),
    CarouselCard("Tasks", "12 pending", Icons.AutoMirrored.Filled.Assignment, Color(0xFFF9A825)),
    CarouselCard("Messages", "5 unread", Icons.AutoMirrored.Filled.Message, Color(0xFF6650A4)),
    CarouselCard("Rewards", "320 points", Icons.Filled.CardGiftcard, Color(0xFFC2185B))
)

private val stats = listOf(
    Stat("Total Orders", "1,284", Icons.Filled.ShoppingCart, Color(0xFF1565C0)),
    Stat("Active Projects", "37", Icons.Filled.TrendingUp, Color(0xFF2E7D32)),
    Stat("Notifications", "12", Icons.Filled.Notifications, Color(0xFFF9A825)),
    Stat("Messages", "5", Icons.AutoMirrored.Filled.Message, Color(0xFF6650A4))
)

private val quickActions = listOf(
    QuickAction("Payments", Icons.Filled.Payment, "qa_payments"),
    QuickAction("Settings", Icons.Filled.Settings, "qa_settings"),
    QuickAction("Orders", Icons.Filled.ShoppingCart, "qa_orders"),
    QuickAction("History", Icons.Filled.History, "qa_history"),
    QuickAction("Favorites", Icons.Filled.Favorite, "qa_favorites"),
    QuickAction("Downloads", Icons.Filled.Download, "qa_downloads")
)

@Composable
fun HomeTab(viewModel: AppViewModel, displayName: String) {
    val activities by viewModel.observeActivities().collectAsStateWithLifecycle(initialValue = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_feed"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { TopSection(displayName) }
        item { SectionTitle("Explore") }
        item { Carousel() }
        item { SectionTitle("Statistics") }
        item { StatisticsGrid() }
        item { SectionTitle("Quick Actions") }
        item { QuickActionsGrid() }
        item { SectionTitle("Recent Feed") }
        items(activities, key = { it.id }) { activity ->
            FeedRow(activity)
        }
        item { Spacer(Modifier.height(72.dp)) }
    }
}

@Composable
private fun TopSection(displayName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("top_section"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer) {
            Box(modifier = Modifier.size(52.dp), contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "User avatar",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(30.dp)
                        .testTag("user_avatar")
                )
            }
        }
        Spacer(Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("Welcome back,", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(displayName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.surfaceVariant) {
            Box(modifier = Modifier.size(44.dp), contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.testTag("home_notification_icon")
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun Carousel() {
    val pagerState = rememberPagerState(pageCount = { carouselCards.size })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 48.dp),
        pageSpacing = 12.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("carousel")
    ) { page ->
        val card = carouselCards[page]
        Card(
            colors = CardDefaults.cardColors(containerColor = card.color),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .testTag("carousel_card_${card.title.lowercase()}")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Icon(card.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
                Spacer(Modifier.weight(1f))
                Text(card.title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(card.subtitle, color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun StatisticsGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.testTag("stats_section")) {
        stats.chunked(2).forEach { rowStats ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowStats.forEach { stat ->
                    StatCard(stat, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun StatCard(stat: Stat, modifier: Modifier = Modifier) {
    Card(modifier = modifier.testTag("stat_${stat.label.replace(" ", "_").lowercase()}")) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(stat.color.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(stat.icon, contentDescription = null, tint = stat.color, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.height(10.dp))
            Text(stat.value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(stat.label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun QuickActionsGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.testTag("quick_actions")) {
        quickActions.chunked(3).forEach { rowActions ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                rowActions.forEach { action ->
                    QuickActionButton(action, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun QuickActionButton(action: QuickAction, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { }
            .testTag(action.tag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(action.icon, contentDescription = action.label, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
            Spacer(Modifier.height(6.dp))
            Text(action.label, fontSize = 12.sp)
        }
    }
}

@Composable
private fun FeedRow(activity: ActivityEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("feed_item_${activity.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val color = categoryColor(activity.category)
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(color.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(categoryIcon(activity.category), contentDescription = activity.category, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(activity.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, maxLines = 1)
                Text(activity.subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
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

private fun categoryColor(category: String): Color = when (category) {
    "Transaction" -> Color(0xFF2E7D32)
    "Notification" -> Color(0xFFF9A825)
    "Order" -> Color(0xFF1565C0)
    else -> Color(0xFF6650A4)
}

private fun categoryIcon(category: String): ImageVector = when (category) {
    "Transaction" -> Icons.Filled.Payment
    "Notification" -> Icons.Filled.Notifications
    "Order" -> Icons.Filled.ShoppingCart
    else -> Icons.Filled.History
}
