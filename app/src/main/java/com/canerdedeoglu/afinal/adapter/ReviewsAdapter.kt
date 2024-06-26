package com.canerdedeoglu.afinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.canerdedeoglu.afinal.R
import com.canerdedeoglu.afinal.models.Review

class ReviewAdapter(private val reviewList: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reviews_card, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.reviewerName.text = review.reviewerName
        holder.reviewDate.text = review.date
        holder.reviewerEmail.text = review.reviewerEmail
        holder.reviewComment.text = review.comment
        holder.reviewRatingBar.rating = review.rating.toFloat()
    }

    override fun getItemCount(): Int = reviewList.size

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reviewerName: TextView = itemView.findViewById(R.id.reviewerName)
        val reviewDate: TextView = itemView.findViewById(R.id.reviewDate)
        val reviewerEmail: TextView = itemView.findViewById(R.id.reviewerEmail)
        val reviewComment: TextView = itemView.findViewById(R.id.reviewComment)
        val reviewRatingBar: RatingBar = itemView.findViewById(R.id.reviewRatingBar)
    }
}
