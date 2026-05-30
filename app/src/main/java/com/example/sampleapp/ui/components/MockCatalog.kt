package com.example.sampleapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/** A single row of mock data shown inside a detail sheet. */
data class DetailEntry(
    val title: String,
    val subtitle: String,
    val trailing: String = "",
    val icon: ImageVector = Icons.Filled.CheckCircle,
    val accent: Color = Color(0xFF6650A4)
)

/** A full detail view: a headline figure plus a list of mock entries. */
data class DetailScreen(
    val title: String,
    val headline: String,
    val headlineValue: String,
    val entries: List<DetailEntry>
)

private val green = Color(0xFF2E7D32)
private val blue = Color(0xFF1565C0)
private val amber = Color(0xFFF9A825)
private val purple = Color(0xFF6650A4)
private val pink = Color(0xFFC2185B)
private val red = Color(0xFFC62828)

/**
 * Static, fully offline catalogue of realistic-looking mock data. Every tappable
 * tile in the app pulls a [DetailScreen] from here so the UI feels like a real app.
 */
object MockCatalog {

    fun forQuickAction(label: String): DetailScreen = when (label) {
        "Payments" -> DetailScreen(
            "Payments", "Wallet balance", "$2,480.55",
            listOf(
                DetailEntry("Visa •••• 4291", "Expires 08/27 · Default", "Active", Icons.Filled.CreditCard, blue),
                DetailEntry("Mastercard •••• 8830", "Expires 11/26", "Active", Icons.Filled.CreditCard, amber),
                DetailEntry("Wallet top-up", "Yesterday, 18:24", "+$200.00", Icons.Filled.AccountBalanceWallet, green),
                DetailEntry("Netflix subscription", "Auto-pay · Monthly", "-$15.99", Icons.Filled.CreditCard, red),
                DetailEntry("Spotify Premium", "Auto-pay · Monthly", "-$9.99", Icons.Filled.CreditCard, red),
                DetailEntry("Refund — Globex", "2 days ago", "+$42.00", Icons.Filled.AccountBalanceWallet, green)
            )
        )
        "Orders" -> DetailScreen(
            "Orders", "Orders this month", "18",
            listOf(
                DetailEntry("Order #10389", "Wireless Headphones", "Delivered", Icons.Filled.CheckCircle, green),
                DetailEntry("Order #10377", "Mechanical Keyboard", "Out for delivery", Icons.Filled.LocalShipping, blue),
                DetailEntry("Order #10366", "USB-C Hub", "Processing", Icons.Filled.Pending, amber),
                DetailEntry("Order #10358", "Laptop Stand", "Delivered", Icons.Filled.CheckCircle, green),
                DetailEntry("Order #10342", "Desk Lamp", "Returned", Icons.Filled.Pending, red),
                DetailEntry("Order #10330", "Notebook (3-pack)", "Delivered", Icons.Filled.CheckCircle, green)
            )
        )
        "History" -> DetailScreen(
            "History", "Events logged", "240",
            listOf(
                DetailEntry("Signed in", "Today, 09:12 · Pixel 8", "", Icons.Filled.AccountCircle, purple),
                DetailEntry("Updated profile photo", "Yesterday, 20:01", "", Icons.Filled.Image, blue),
                DetailEntry("Changed password", "Mar 12, 14:33", "", Icons.Filled.Lock, amber),
                DetailEntry("Exported report", "Mar 10, 11:07", "PDF", Icons.Filled.PictureAsPdf, red),
                DetailEntry("Enabled dark mode", "Mar 09, 22:40", "", Icons.Filled.Bolt, purple),
                DetailEntry("Created task “Q2 Plan”", "Mar 08, 08:15", "", Icons.AutoMirrored.Filled.Assignment, green)
            )
        )
        "Favorites" -> DetailScreen(
            "Favorites", "Saved items", "12",
            listOf(
                DetailEntry("Analytics Dashboard", "Pinned report", "★ 4.9", Icons.Filled.Insights, blue),
                DetailEntry("Acme Corp", "Vendor", "★ 4.7", Icons.Filled.Star, amber),
                DetailEntry("Monthly Summary", "Document", "★ 4.8", Icons.Filled.Description, green),
                DetailEntry("Design Team", "Conversation", "★ 5.0", Icons.AutoMirrored.Filled.Message, purple),
                DetailEntry("Wireless Headphones", "Product", "★ 4.6", Icons.Filled.Favorite, pink)
            )
        )
        "Downloads" -> DetailScreen(
            "Downloads", "Storage used", "1.8 GB",
            listOf(
                DetailEntry("Annual_Report_2025.pdf", "12.4 MB · Completed", "100%", Icons.Filled.PictureAsPdf, red),
                DetailEntry("design_mockups.zip", "84.1 MB · Completed", "100%", Icons.Filled.Download, blue),
                DetailEntry("intro_video.mp4", "256 MB · Downloading", "62%", Icons.Filled.MovieCreation, purple),
                DetailEntry("podcast_ep12.mp3", "48.2 MB · Completed", "100%", Icons.Filled.MusicNote, green),
                DetailEntry("invoice_4821.pdf", "0.4 MB · Completed", "100%", Icons.AutoMirrored.Filled.ReceiptLong, amber)
            )
        )
        "Settings" -> DetailScreen(
            "Settings", "Quick settings", "—",
            listOf(
                DetailEntry("Account", "Manage your account", "", Icons.Filled.AccountCircle, purple),
                DetailEntry("Appearance", "Theme & language", "", Icons.Filled.Bolt, blue),
                DetailEntry("Privacy", "Permissions & data", "", Icons.Filled.VisibilityOff, green),
                DetailEntry("Security", "Password & biometrics", "", Icons.Filled.Fingerprint, amber)
            )
        )
        else -> DetailScreen(label, "Details", "—", emptyList())
    }

    fun forCarousel(title: String): DetailScreen = when (title) {
        "Analytics" -> DetailScreen(
            "Analytics", "Visitors this week", "8,432",
            listOf(
                DetailEntry("Page views", "vs last week", "+12.4%", Icons.Filled.TrendingUp, green),
                DetailEntry("Conversion rate", "vs last week", "+0.8%", Icons.Filled.Insights, blue),
                DetailEntry("Bounce rate", "vs last week", "-3.1%", Icons.Filled.TrendingUp, amber),
                DetailEntry("Avg. session", "Duration", "4m 12s", Icons.Filled.CalendarMonth, purple),
                DetailEntry("New users", "This week", "1,204", Icons.Filled.AccountCircle, pink)
            )
        )
        "Reports" -> DetailScreen(
            "Reports", "Available reports", "9",
            listOf(
                DetailEntry("March Performance", "Generated Mar 31", "PDF", Icons.Filled.PictureAsPdf, red),
                DetailEntry("Q1 Financials", "Generated Apr 02", "XLSX", Icons.Filled.Description, green),
                DetailEntry("User Engagement", "Generated Apr 05", "PDF", Icons.Filled.PictureAsPdf, red),
                DetailEntry("Sales Funnel", "Generated Apr 09", "PDF", Icons.Filled.Insights, blue),
                DetailEntry("Retention Cohorts", "Generated Apr 12", "CSV", Icons.Filled.Description, amber)
            )
        )
        "Tasks" -> DetailScreen(
            "Tasks", "Pending tasks", "12",
            listOf(
                DetailEntry("Finalize Q2 roadmap", "Due tomorrow", "High", Icons.Filled.Pending, red),
                DetailEntry("Review pull request #182", "Due today", "Medium", Icons.AutoMirrored.Filled.Assignment, amber),
                DetailEntry("Reply to design feedback", "Due in 2 days", "Low", Icons.AutoMirrored.Filled.Assignment, green),
                DetailEntry("Update onboarding docs", "Due Apr 20", "Medium", Icons.Filled.Description, blue),
                DetailEntry("Prepare release notes", "Done", "✓", Icons.Filled.Done, green)
            )
        )
        "Messages" -> DetailScreen(
            "Messages", "Unread", "5",
            listOf(
                DetailEntry("Priya Sharma", "Can you review the mockups?", "2m", Icons.Filled.AccountCircle, purple),
                DetailEntry("Design Team", "Liu: shipped the new icons 🎉", "18m", Icons.AutoMirrored.Filled.Message, blue),
                DetailEntry("Alex Morgan", "Standup moved to 10:30", "1h", Icons.Filled.AccountCircle, green),
                DetailEntry("Sara Khan", "Invoice approved ✅", "3h", Icons.Filled.AccountCircle, amber),
                DetailEntry("Support", "Your ticket was resolved", "Yesterday", Icons.Filled.QuestionAnswer, pink)
            )
        )
        "Rewards" -> DetailScreen(
            "Rewards", "Points balance", "320",
            listOf(
                DetailEntry("Daily login bonus", "Today", "+10", Icons.Filled.Bolt, amber),
                DetailEntry("Completed profile", "One-time", "+50", Icons.Filled.CheckCircle, green),
                DetailEntry("Referral — Tom B.", "Apr 08", "+100", Icons.Filled.Star, purple),
                DetailEntry("Redeemed: $5 voucher", "Apr 01", "-150", Icons.Filled.CreditCard, red),
                DetailEntry("Welcome bonus", "Sign-up", "+100", Icons.Filled.Star, blue)
            )
        )
        else -> DetailScreen(title, "Details", "—", emptyList())
    }

    fun forStat(label: String): DetailScreen = when (label) {
        "Total Orders" -> forQuickAction("Orders").copy(title = "Total Orders", headline = "All-time orders", headlineValue = "1,284")
        "Active Projects" -> DetailScreen(
            "Active Projects", "In progress", "37",
            listOf(
                DetailEntry("Mobile App Revamp", "On track · 72%", "Due May 10", Icons.Filled.Bolt, blue),
                DetailEntry("Website Migration", "At risk · 41%", "Due May 18", Icons.Filled.Pending, amber),
                DetailEntry("Data Pipeline", "On track · 88%", "Due Apr 29", Icons.Filled.CheckCircle, green),
                DetailEntry("Brand Refresh", "On track · 60%", "Due Jun 02", Icons.Filled.Image, purple),
                DetailEntry("API v2", "Blocked · 25%", "Due Jun 14", Icons.Filled.Pending, red)
            )
        )
        "Notifications" -> forCarousel("Messages").copy(title = "Notifications", headline = "New notifications", headlineValue = "12")
        "Messages" -> forCarousel("Messages")
        else -> DetailScreen(label, "Details", "—", emptyList())
    }

    fun securitySettings() = DetailScreen(
        "Security", "Account protection", "Strong",
        listOf(
            DetailEntry("Password", "Last changed 18 days ago", "Strong", Icons.Filled.Password, green),
            DetailEntry("Two-factor authentication", "SMS + Authenticator", "On", Icons.Filled.Lock, green),
            DetailEntry("Biometric unlock", "Fingerprint", "On", Icons.Filled.Fingerprint, blue),
            DetailEntry("Trusted devices", "3 devices", "Manage", Icons.Filled.AccountCircle, purple),
            DetailEntry("Login alerts", "Email on new sign-in", "On", Icons.Filled.CheckCircle, amber)
        )
    )

    fun privacySettings() = DetailScreen(
        "Privacy", "Your data", "Protected",
        listOf(
            DetailEntry("Profile visibility", "Visible to contacts", "Contacts", Icons.Filled.VisibilityOff, blue),
            DetailEntry("Activity status", "Show when online", "On", Icons.Filled.Bolt, green),
            DetailEntry("Personalized ads", "Disabled", "Off", Icons.Filled.VisibilityOff, red),
            DetailEntry("Data download", "Export your data", "Request", Icons.Filled.Download, purple),
            DetailEntry("Analytics sharing", "Anonymous only", "Limited", Icons.Filled.Insights, amber)
        )
    )

    fun notificationSettings() = DetailScreen(
        "Notifications", "Channels", "6 active",
        listOf(
            DetailEntry("Push notifications", "All devices", "On", Icons.Filled.CheckCircle, green),
            DetailEntry("Email digests", "Weekly summary", "Weekly", Icons.Filled.Description, blue),
            DetailEntry("Order updates", "Shipping & delivery", "On", Icons.Filled.LocalShipping, amber),
            DetailEntry("Promotions", "Offers & rewards", "Off", Icons.Filled.Star, red),
            DetailEntry("Mentions", "When someone @mentions you", "On", Icons.AutoMirrored.Filled.Message, purple)
        )
    )

    fun helpCenter() = DetailScreen(
        "Help Center", "Popular topics", "—",
        listOf(
            DetailEntry("How do I reset my password?", "Account & security", "", Icons.Filled.QuestionAnswer, blue),
            DetailEntry("Where can I see my orders?", "Orders & shipping", "", Icons.Filled.QuestionAnswer, green),
            DetailEntry("How do I enable dark mode?", "Appearance", "", Icons.Filled.QuestionAnswer, purple),
            DetailEntry("How are rewards calculated?", "Rewards", "", Icons.Filled.QuestionAnswer, amber),
            DetailEntry("Contact support", "Avg. reply: 2h", "Chat", Icons.Filled.QuestionAnswer, pink)
        )
    )
}
