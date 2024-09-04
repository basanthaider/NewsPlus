package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.adapters.CategoriesAdapter
import com.example.newsapp.databinding.FragmentCategoryBinding


class CategoryFragment() : Fragment() {
    private lateinit var gridView: GridView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val gridView = binding.categoriesGridView
        val adapter = CategoriesAdapter(requireContext())
        gridView.adapter = adapter
        binding.categoriesGridView.onItemClickListener = AdapterView.OnItemClickListener { _, view, _, _ ->
            val category = view.findViewById<TextView>(R.id.categoryTV).text.toString()
            val action = CategoryFragmentDirections.actionHomeToHeadlinesFragment(category)
            findNavController().navigate(action)
        }
        return binding.root
    }
}
