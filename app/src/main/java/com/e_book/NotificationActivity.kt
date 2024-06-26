package com.e_book
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e_book.model.NotificationResponseItem
import com.e_book.retrofit.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationActivity : ThemeActivity() {
    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private val notifications = mutableListOf<NotificationResponseItem>()
    private var notificationCount = 0
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)

        notificationRecyclerView = findViewById(R.id.notificationRecyclerView)

        notificationAdapter = NotificationAdapter(notifications) { position ->
            markAsRead(position)
        }

        notificationRecyclerView.adapter = notificationAdapter
        notificationRecyclerView.layoutManager = LinearLayoutManager(this)

        loadNotifications()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadNotifications() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiRepository.getNotifications()
                if (response.isSuccessful) {
                    val notificationResponse = response.body()
                    notificationResponse?.let {
                        notifications.clear()
                        notifications.addAll(it)
                        // Calculate unread notification count
                        notificationCount = notifications.count { notification ->
                            !notification.readAt.equals("Read", ignoreCase = false)
                        }
                        notificationAdapter.notifyDataSetChanged()
                        // Update notification count in SharedPreferences
                        updateNotificationCount(notificationCount)
                    }
                    Log.d("NotificationActivity", "API call successful")
                } else {
                    val errorMessage = response.message()
                    Log.e("NotificationActivity", "API call failed: $errorMessage")
                    Toast.makeText(this@NotificationActivity, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("NotificationActivity", "Exception occurred: ${e.message}", e)
                Toast.makeText(this@NotificationActivity, "An error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun markAsRead(position: Int) {
        val notification = notifications[position]
        if (notification.readAt != "Read") {
            notification.readAt = "Read"
            notificationAdapter.notifyItemChanged(position)

            // Save read status in SharedPreferences using notificationId as the key
            sharedPreferences?.edit {
                putBoolean(notification.notificationId.toString(), true)
            }

            // Update notification count in SharedPreferences and local variable
            if (notificationCount > 0) {
                notificationCount -= 1
                sharedPreferences?.edit {
                    putInt("notification_count", notificationCount)
                }
            }
        }
    }

    private fun updateNotificationCount(count: Int) {
        // Update count in SharedPreferences
        sharedPreferences?.edit {
            putInt("notification_count", count)
        }
    }
}
