package com.e_book.Offers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e_book.R
import com.e_book.ThemeActivity
import com.e_book.model.CouponResponsItem
import com.e_book.retrofit.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Available_OffersActivity : ThemeActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var couponAdapter: CouponAdapter
    private val apiRepository = ApiRepository // Correctly instantiate ApiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_offers)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        fetchCoupons()
    }

    private fun fetchCoupons() {
        lifecycleScope.launch {
            val response = withContext(Dispatchers.IO) { apiRepository.getCoupons() }
            if (response.isSuccessful) {
                response.body()?.let { couponList: List<CouponResponsItem> ->
                    couponAdapter = CouponAdapter(couponList)
                    recyclerView.adapter = couponAdapter
                }
            } else {
                // Handle error
            }
        }
    }
}
