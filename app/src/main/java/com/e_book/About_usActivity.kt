package com.e_book
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import android.widget.ImageView

class About_usActivity:ThemeActivity() {

    private lateinit var imageSlider: ViewPager2
    private lateinit var adapter: ImageSliderAdapter
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        imageSlider = findViewById(R.id.imageSlider)
        val leftArrow: ImageView = findViewById(R.id.leftArrow)
        val rightArrow: ImageView = findViewById(R.id.rightArrow)

        val imageList = listOf(
            R.drawable.appslider2, // Replace with your images
            R.drawable.appslider3,
            R.drawable.appslider1,
            R.drawable.appslider4,
        )

        adapter = ImageSliderAdapter(this, imageList)
        imageSlider.adapter = adapter

        // Auto-slide functionality
        val autoSlideRunnable = object : Runnable {
            override fun run() {
                if (currentPage == imageList.size) {
                    currentPage = 0
                }
                imageSlider.setCurrentItem(currentPage++, true)
                handler.postDelayed(this, 3000) // Change slide interval here
            }
        }
        handler.postDelayed(autoSlideRunnable, 3000)

        // Handle left and right navigation
        leftArrow.setOnClickListener {
            if (currentPage > 0) {
                imageSlider.setCurrentItem(--currentPage, true)
            }
        }
        rightArrow.setOnClickListener {
            if (currentPage < imageList.size - 1) {
                imageSlider.setCurrentItem(++currentPage, true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
