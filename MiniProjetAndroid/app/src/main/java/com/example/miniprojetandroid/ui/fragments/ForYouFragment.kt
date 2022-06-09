package com.example.miniprojetandroid.ui.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.adapters.NewsAdapter
import tn.esprit.curriculumvitae.data.News

class ForYouFragment : Fragment() {

    lateinit var newsRecyclerView: RecyclerView
    lateinit var newsAdapter: NewsAdapter

    lateinit var newsList : MutableList<News>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView =  inflater.inflate(R.layout.fragment_for_you, container, false)

        newsRecyclerView = rootView.findViewById(R.id.recyclerNews)

        val data = ArrayList<News>()
        val dec : String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."

        val n = News("Lorem ipsum ", "R.drawable.logo4",dec)
        data.add(n)
        data.add(n)
        data.add(n)

        newsList = data

        newsAdapter = NewsAdapter(newsList)
        newsRecyclerView.adapter = newsAdapter
        newsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,
            false)
        // Inflate the layout for this fragment
        return rootView

    }
}