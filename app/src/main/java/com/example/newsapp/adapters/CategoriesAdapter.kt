package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.CategoriesListItemBinding
import com.example.newsapp.ui.fragments.CategoryFragment
import androidx.navigation.fragment.findNavController
import com.example.newsapp.ui.fragments.CategoryFragmentDirections

class CategoriesAdapter(
    private val fragment: CategoryFragment // Use the fragment directly for navigation
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private val images = intArrayOf(
        R.drawable.health,
        R.drawable.education,
        R.drawable.business,
        R.drawable.politics,
        R.drawable.entertainment,
        R.drawable.enviroment,
        R.drawable.food,
        R.drawable.crime,
        R.drawable.science,
        R.drawable.sports,
        R.drawable.tourism,
        R.drawable.domestic,
        R.drawable.technology,
        R.drawable.top,
        R.drawable.world,
        R.drawable.others,
    )

    private val categories = arrayOf(
        "Health",
        "Education",
        "Business",
        "Politics",
        "Entertainment",
        "Environment",
        "Food",
        "Crime",
        "Science",
        "Sports",
        "Tourism",
        "Domestic",
        "Technology",
        "Top",
        "World",
        "Other",
    )

    inner class CategoryViewHolder(val binding: CategoriesListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String, image: Int) {
            binding.categoryTV.text = category
            Glide.with(binding.categoryImage.context).load(image).into(binding.categoryImage)

            binding.newsItemContainer.setOnClickListener {
                val action = CategoryFragmentDirections.actionHomeToHeadlinesFragment(category)
                fragment.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], images[position])
    }

    override fun getItemCount() = images.size
}
