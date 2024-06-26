package com.e_book.Offers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e_book.R
import com.e_book.model.CouponResponsItem

class CouponAdapter(private val couponList: List<CouponResponsItem>) : RecyclerView.Adapter<CouponAdapter.CouponViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_coupon, parent, false)
        return CouponViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        val currentCoupon = couponList[position]
        holder.tvCouponCode.text = currentCoupon.couponCode
        holder.tvDiscount.text = "Discount: ${currentCoupon.discount}"
        holder.tvExpirationDate.text = "Expires on: ${currentCoupon.expirationDate}"
        holder.tvValidityStatus.text = if (currentCoupon.isActive) "Valid" else "Invalid"
        holder.tvValidityStatus.setTextColor(
            holder.itemView.context.resources.getColor(
                if (currentCoupon.isActive) android.R.color.holo_green_dark else android.R.color.holo_red_dark
            )
        )
    }

    override fun getItemCount() = couponList.size

    class CouponViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCouponCode: TextView = itemView.findViewById(R.id.tv_coupon_code)
        val tvDiscount: TextView = itemView.findViewById(R.id.tv_discount)
        val tvExpirationDate: TextView = itemView.findViewById(R.id.tv_expiration_date)
        val tvValidityStatus: TextView = itemView.findViewById(R.id.tv_validity_status)
    }
}
