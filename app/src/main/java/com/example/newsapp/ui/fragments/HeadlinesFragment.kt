package com.example.newsapp.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.adapters.HeadlinesAdapter
import com.example.newsapp.api.NewsCallable
import com.example.newsapp.databinding.FragmentHeadlinesBinding
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.util.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HeadlinesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        val args = HeadlinesFragmentArgs.fromBundle(requireArguments())
        val category = args.categoryName
        binding.progressBar.isVisible = true
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val countryCode = sharedPreferences.getString("chosenCountry", "DEFAULT") ?: "us"
        loadNews(binding, requireContext(), category, countryCode)
        return binding.root
    }

    private fun loadNews(binding: FragmentHeadlinesBinding, context: Context, category: String, country: String) {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.d("trace", "CountryCode: $country")
        val callable = retrofit.create(NewsCallable::class.java)
        callable.getNews(category, country).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val news = response.body()?.articles
                Log.d("trace", "Response: $news")
                showNews(binding, context as Activity, news as ArrayList<Article>)
                binding.progressBar.isVisible = false
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }

        })
    }

    private fun showNews(binding: FragmentHeadlinesBinding, activity: Activity, news: ArrayList<Article>) {
        val adapter = HeadlinesAdapter(activity, news)
        binding.recyclerHeadlines.adapter = adapter
    }
}
