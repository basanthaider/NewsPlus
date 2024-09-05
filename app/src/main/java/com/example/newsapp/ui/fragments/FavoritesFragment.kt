package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapters.FavoritesAdapter
import com.example.newsapp.databinding.FragmentFavoritesBinding
import com.example.newsapp.models.Article
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesAdapter
    private val articles = ArrayList<Article>()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.VISIBLE

        setupRecyclerView()
        fetchFavorites()

        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerFavs
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FavoritesAdapter(requireActivity(), articles) { removedArticle ->
            articles.remove(removedArticle)
            adapter.notifyDataSetChanged()
        }
        recyclerView.adapter = adapter
    }

    private fun fetchFavorites() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .get().addOnSuccessListener { documents ->
                    articles.clear()
                    for (document in documents) {
                        val article = Article(
                            id = document.id,
                            title = document.getString("title") ?: "",
                            description = document.getString("description") ?: "",
                            link = document.getString("url") ?: "",
                            imgUrl = document.getString("urlToImage") ?: ""
                        )
                        articles.add(article)
                    }
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                    recyclerView.visibility = if (articles.isEmpty()) View.GONE else View.VISIBLE
                }.addOnFailureListener { exception ->
                    Log.e("FavoritesFragment", "Error fetching favorites", exception)
                    binding.progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
        } else {
            binding.progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }
    }
}
