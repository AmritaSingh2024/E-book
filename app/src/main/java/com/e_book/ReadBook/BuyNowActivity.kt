package com.e_book.ReadBook

import android.app.DatePickerDialog
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.e_book.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*

class BuyNowActivity : AppCompatActivity() {

    private lateinit var etPrice: EditText
    private lateinit var etCouponCode: EditText
    private lateinit var tvSelectedDate: TextView
    private lateinit var btnApplyCoupon: Button
    private lateinit var btnPay: Button
    private lateinit var ivQrCode: ImageView

    // Define some example coupon codes and their discounts
    private val couponDiscounts = mapOf(
        "DISCOUNT10" to 0.10,
        "DISCOUNT20" to 0.20,
        "DISCOUNT30" to 0.30
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_now)

        etPrice = findViewById(R.id.et_price)
        etCouponCode = findViewById(R.id.et_coupon_code)
        tvSelectedDate = findViewById(R.id.tv_selected_date)
        btnApplyCoupon = findViewById(R.id.btn_apply_coupon)
        btnPay = findViewById(R.id.btn_pay)
        ivQrCode = findViewById(R.id.iv_qr_code)

        btnApplyCoupon.setOnClickListener {
            applyCoupon()
        }

        btnPay.setOnClickListener {
            pay()
        }
    }

    fun showDatePicker(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            tvSelectedDate.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun applyCoupon() {
        val priceText = etPrice.text.toString()
        val couponCode = etCouponCode.text.toString()

        if (priceText.isNotEmpty()) {
            val originalPrice = priceText.toDouble()
            val discount = couponDiscounts[couponCode]

            if (discount != null) {
                val discountedPrice = originalPrice - (originalPrice * discount)
                etPrice.setText(discountedPrice.toString())
                etCouponCode.setError(null)
            } else {
                etCouponCode.setError("Invalid coupon code")
            }
        } else {
            etPrice.setError("Enter a valid price")
        }
    }

    private fun pay() {
        val finalPrice = etPrice.text.toString()
        if (finalPrice.isNotEmpty()) {
            val qrCodeBitmap = generateQrCode(finalPrice)
            if (qrCodeBitmap != null) {
                ivQrCode.setImageBitmap(qrCodeBitmap)
                ivQrCode.visibility = ImageView.VISIBLE
            }
        }
    }

    private fun generateQrCode(text: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        return try {
            val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}

