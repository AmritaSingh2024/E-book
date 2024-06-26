package com.e_book

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.e_book.R
import com.e_book.RoomDatabase.AppDatabase
import com.e_book.viewmodel.FeedbackViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class FeedBackActivity : ThemeActivity() {
    private var userId by Delegates.notNull<Int>()
    private val feedbackViewModel: FeedbackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        // Fetch userId asynchronously using coroutines
        GlobalScope.launch(Dispatchers.IO) {
            userId = AppDatabase.getDatabase(this@FeedBackActivity).appDao().getUserId()
        }

        val titleEditText: EditText = findViewById(R.id.titleEditText)
        val messageEditText: EditText = findViewById(R.id.messageEditText)
        val submitButton: Button = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val message = messageEditText.text.toString()

            if (title.isNotEmpty() && message.isNotEmpty()) {
                feedbackViewModel.submitFeedback(userId, title, message)
            } else {
                Toast.makeText(this, "Title and message cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        feedbackViewModel.feedbackResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Toast.makeText(this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to submit feedback", Toast.LENGTH_SHORT).show()
            }
        })

        feedbackViewModel.feedbackError.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }
}
