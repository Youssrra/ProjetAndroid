package com.example.miniprojetandroid.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.R
import tn.esprit.curriculumvitae.data.News

class NewsAdapter (val newsList: MutableList<News>) : RecyclerView.Adapter<NewsAdapter.ExperienceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExperienceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_singele_row, parent, false)
        return ExperienceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {

        val news = newsList[position]

        holder.newsImage.setImageURI(Uri.parse(news.newsImage))
        holder.newsTitle.text = news.newsTitle
        holder.newsDescription.text = news.newsDescription


    }

    override fun getItemCount(): Int = newsList.size

    class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage = itemView.findViewById<ImageView>(R.id.newsImage)
        val newsTitle = itemView.findViewById<TextView>(R.id.newsTitle)
        val newsDescription = itemView.findViewById<TextView>(R.id.newsDescription)

    }
}