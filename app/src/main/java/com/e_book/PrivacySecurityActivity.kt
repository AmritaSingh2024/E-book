package com.e_book
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PrivacySecurityActivity : ThemeActivity() {
    private lateinit var backButton: Button // Declare backButton as lateinit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_security)

        backButton = findViewById(R.id.backButton) // Initialize backButton using findViewById

        // Set a click listener for the backButton
        backButton.setOnClickListener {
            finish() // Closes the activity and goes back to the previous screen
        }
    }
}
