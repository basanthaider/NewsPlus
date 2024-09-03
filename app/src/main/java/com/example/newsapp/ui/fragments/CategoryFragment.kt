package com.example.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.contentcapture.ContentCaptureSession
import android.widget.GridView
import com.example.newsapp.R
import com.example.newsapp.adapters.CategoriesAdapter


class CategoryFragment() : Fragment() {
    private lateinit var gridView: GridView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        gridView = view.findViewById(R.id.categoriesGridView)
        val adapter = CategoriesAdapter(requireContext())
        gridView.adapter = adapter
        return view
    }
}
