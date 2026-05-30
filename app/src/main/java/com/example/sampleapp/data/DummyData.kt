package com.example.sampleapp.data

import com.example.sampleapp.data.entity.ActivityEntity

/**
 * Deterministic, fully offline dummy data generator. Produces realistic-looking
 * feed entries (activities, notifications, transactions, orders) seeded on first run.
 */
object DummyData {

    private val activityTitles = listOf(
        "Logged in from a new device",
        "Profile photo updated",
        "Password changed successfully",
        "New comment on your report",
        "Weekly summary is ready",
        "Project milestone reached",
        "Document shared with you",
        "Task assigned to you",
        "Reminder: meeting at 3 PM",
        "Backup completed"
    )

    private val notificationTitles = listOf(
        "Your subscription renews soon",
        "New feature available",
        "Security alert reviewed",
        "3 new messages",
        "App update installed",
        "Storage almost full",
        "Promo: 20% off rewards",
        "Friend request accepted"
    )

    private val transactionTitles = listOf(
        "Payment to Acme Corp",
        "Refund from Globex",
        "Subscription — Pro Plan",
        "Wallet top-up",
        "Transfer to savings",
        "Withdrawal to bank",
        "Invoice #4821 paid",
        "Cashback reward"
    )

    private val orderTitles = listOf(
        "Order #10293 shipped",
        "Order #10311 delivered",
        "Order #10330 processing",
        "Order #10342 confirmed",
        "Order #10358 out for delivery",
        "Order #10366 returned",
        "Order #10377 packed",
        "Order #10389 placed"
    )

    private val people = listOf(
        "Alex Morgan", "Priya Sharma", "Liu Wei", "Sara Khan",
        "John Carter", "Maria Garcia", "Tom Becker", "Aisha Bello"
    )

    /**
     * Builds at least 50 dummy activity rows across four categories.
     */
    fun activities(): List<ActivityEntity> {
        val list = mutableListOf<ActivityEntity>()
        var counter = 0

        fun nextTime(): String {
            val hour = (counter * 37) % 24
            val minute = (counter * 53) % 60
            counter++
            return "%02d:%02d".format(hour, minute)
        }

        // 14 activities
        activityTitles.forEachIndexed { i, t ->
            list += ActivityEntity(
                title = t,
                subtitle = "by ${people[i % people.size]}",
                category = "Activity",
                amount = "",
                timestamp = nextTime()
            )
        }
        repeat(4) { i ->
            list += ActivityEntity(
                title = activityTitles[i],
                subtitle = "by ${people[(i + 3) % people.size]}",
                category = "Activity",
                amount = "",
                timestamp = nextTime()
            )
        }

        // 12 notifications
        repeat(12) { i ->
            list += ActivityEntity(
                title = notificationTitles[i % notificationTitles.size],
                subtitle = "Tap to view details",
                category = "Notification",
                amount = "",
                timestamp = nextTime(),
                read = i % 3 == 0
            )
        }

        // 12 transactions
        repeat(12) { i ->
            val sign = if (i % 2 == 0) "-" else "+"
            val value = 5 + (i * 17) % 480
            list += ActivityEntity(
                title = transactionTitles[i % transactionTitles.size],
                subtitle = if (sign == "-") "Debit" else "Credit",
                category = "Transaction",
                amount = "$sign$$value.00",
                timestamp = nextTime()
            )
        }

        // 12 orders
        repeat(12) { i ->
            val value = 12 + (i * 23) % 300
            list += ActivityEntity(
                title = orderTitles[i % orderTitles.size],
                subtitle = "${1 + i % 5} item(s)",
                category = "Order",
                amount = "$$value.00",
                timestamp = nextTime()
            )
        }

        return list // 18 + 12 + 12 + 12 = 54 rows
    }
}
