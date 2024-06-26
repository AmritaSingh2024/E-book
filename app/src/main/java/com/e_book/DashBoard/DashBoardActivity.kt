package com.e_book.DashBoard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.e_book.*
import com.e_book.MaterialList.MaterialActivity
import com.e_book.ReadBook.ListBookActivity
import com.e_book.AllBookList.Extend_AccessActivity
import com.e_book.Offers.Available_OffersActivity
import com.e_book.Profile.ProfileActivity
import com.e_book.RoomDatabase.AppDatabase
import com.e_book.StickyNotes.StickyNotesActivity
import com.e_book.retrofit.ApiRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class DashBoardActivity : ThemeActivity() {
    private val BASE_URL = "https://ebook.varcas.org/"
    private val LOGOUT_ENDPOINT = "api/Account/Logout/Logout"
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImageSliderAdapter
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private lateinit var sharedPreferences: SharedPreferences
    private var notificationCount = 0
    private var badge: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)

        // Sample images for the slider
        val imageList = listOf(
            R.drawable.appslider1,
            R.drawable.appslider2,
            R.drawable.appslider3,
            R.drawable.appslider4
        )

        // Setup ViewPager2 with adapter
        viewPager = findViewById(R.id.imageSlider)
        adapter = ImageSliderAdapter(this, imageList)
        viewPager.adapter = adapter

        // Auto-slide functionality
        val autoSlideRunnable = object : Runnable {
            override fun run() {
                if (currentPage == imageList.size) {
                    currentPage = 0
                }
                viewPager.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, 3000) // 3000 milliseconds delay (3 seconds)
            }
        }
        handler.postDelayed(autoSlideRunnable, 3000)

        // Handle left and right navigation
        val leftArrow = findViewById<ImageView>(R.id.leftArrow)
        val rightArrow = findViewById<ImageView>(R.id.rightArrow)

        leftArrow.setOnClickListener {
            if (currentPage > 0) {
                viewPager.setCurrentItem(--currentPage, true)
            }
        }
        rightArrow.setOnClickListener {
            if (currentPage < imageList.size - 1) {
                viewPager.setCurrentItem(++currentPage, true)
            }
        }

        findViewById<TextView>(R.id.viewBooklist).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, ListBookActivity::class.java))
        }
        findViewById<TextView>(R.id.Availablebook).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, MaterialActivity::class.java))
        }
        findViewById<TextView>(R.id.stickynotes).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, StickyNotesActivity::class.java))
        }
        findViewById<TextView>(R.id.extendbook).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, Extend_AccessActivity::class.java))
        }
        findViewById<TextView>(R.id.availableofferse).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, Available_OffersActivity::class.java))
        }
        findViewById<TextView>(R.id.feedback).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, FeedBackActivity::class.java))
        }

        findViewById<CardView>(R.id.BookListCard).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, MaterialActivity::class.java))
        }
        findViewById<CardView>(R.id.ReadBookCard).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, ListBookActivity::class.java))
        }
        findViewById<CardView>(R.id.stickynotescard).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, StickyNotesActivity::class.java))
        }
        findViewById<CardView>(R.id.extendaccesscard).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, Extend_AccessActivity::class.java))
        }
        findViewById<CardView>(R.id.availableofferseCard).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, Available_OffersActivity::class.java))
        }
        findViewById<CardView>(R.id.feedBackCard).setOnClickListener {
            startActivity(Intent(this@DashBoardActivity, FeedBackActivity::class.java))
        }



        // Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> true
                R.id.about -> {
                    startActivity(Intent(this, About_usActivity::class.java))
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.security -> {
                    startActivity(Intent(this, PrivacySecurityActivity::class.java))
                    true
                }
                R.id.change_password -> {
                    startActivity(Intent(this, ChangePasswordActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Load initial notifications count
        loadNotifications()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)

        // Setup notification badge
        val menuItem = menu?.findItem(R.id.notification)
        val actionView = menuItem?.actionView
        badge = actionView?.findViewById(R.id.badge)
        // Update the count here
        updateNotificationBadge()
        actionView?.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                showLogoutDialog()
                true
            }
            R.id.notification -> {
                startActivity(Intent(this, NotificationActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this@DashBoardActivity).apply {
            setMessage("Are You Sure You Want to Logout?")
            setTitle("Logout")
            setCancelable(false)
            setPositiveButton("Yes") { _, _ -> logout() }
            setNegativeButton("No") { _, _ ->
                Toast.makeText(this@DashBoardActivity, "Logout Cancelled", Toast.LENGTH_LONG).show()
            }
        }.create().show()
    }

    private fun logout() {
        val url = BASE_URL + LOGOUT_ENDPOINT
        val request = Request.Builder().url(url).post(RequestBody.create(null, "")).build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Logout", "Failed to logout", e)
                runOnUiThread {
                    Toast.makeText(this@DashBoardActivity, "Logout failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    clearUserSession()
                    val intent = Intent(this@DashBoardActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("Logout", "Failed to logout: ${response.code}")
                    runOnUiThread {
                        Toast.makeText(this@DashBoardActivity, "Logout failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun clearUserSession() {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase(this@DashBoardActivity).appDao().deleteAll()
        }
        sharedPreferences.edit().clear().apply()
    }

    override fun onResume() {
        super.onResume()
        // Update notification count from SharedPreferences
        notificationCount = sharedPreferences.getInt("notification_count", 0)
        updateNotificationBadge()
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private fun loadNotifications() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = ApiRepository.getNotifications() // Replace with your actual API call
                if (response.isSuccessful) {
                    val notificationResponse = response.body()
                    notificationResponse?.let {
                        // Calculate unread notification count
                        notificationCount = it.count { notification ->
                            notification.readAt.equals("Read", ignoreCase = true).not()
                        }
                        updateNotificationBadge()
                    }
                    Log.d("DashBoardActivity", "API call successful")
                } else {
                    val errorMessage = response.message()
                    Log.e("DashBoardActivity", "API call failed: $errorMessage")
                    Toast.makeText(this@DashBoardActivity, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("DashBoardActivity", "Exception occurred: ${e.message}", e)
                Toast.makeText(this@DashBoardActivity, "An error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateNotificationBadge() {
        badge?.let {
            if (notificationCount > 0) {
                it.text = notificationCount.toString()
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }
}
