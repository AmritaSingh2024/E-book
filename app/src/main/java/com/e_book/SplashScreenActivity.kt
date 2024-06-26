package com.e_book
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        val splash = findViewById<ImageView>(R.id.splscreen)
        splash.alpha = 0f

        // Animate alpha to 1 with a duration of 1500 milliseconds
        splash.animate().setDuration(1500).alpha(1f).withEndAction {
            // Create intent to start MainActivity
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)

            // Apply transition animation
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            // Finish the current activity
            finish()
        }
    }
}
