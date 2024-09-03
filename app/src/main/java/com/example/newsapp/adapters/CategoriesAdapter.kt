package com.example.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.newsapp.R

class CategoriesAdapter(private val context: Context) : BaseAdapter() {

    private val images = intArrayOf(
        R.drawable.health,
        R.drawable.finance,
        R.drawable.technology,
        R.drawable.sports,
        R.drawable.arts,
        R.drawable.tourism,
    )
    private val categories = arrayOf(
        "Health",
        "Finance",
        "Technology",
        "Sports",
        "Arts",
        "Tourism"
    )

    override fun getCount() = images.size

    override fun getItem(position: Int) = images[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.categories_list_item, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.categoryImage)
        val categoryTextView = view.findViewById<TextView>(R.id.categoryTV)

        imageView.setImageResource(images[position])
        categoryTextView.text = categories[position]

        return view
    }
}
