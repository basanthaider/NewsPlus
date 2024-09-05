package com.example.newsapp.adapters

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.R
import com.example.newsapp.databinding.ArticleListItemBinding
import com.example.newsapp.models.Article
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Base64

class HeadlinesAdapter(private val activity: Activity, private val articles: ArrayList<Article>) :
    Adapter<HeadlinesAdapter.HeadlinesViewHolder>() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    class HeadlinesViewHolder(val binding: ArticleListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        val binding =
            ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeadlinesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        val article = articles[position]
        val articleId = article.id ?: generateIdFromUrl(article.url)
        val userId = auth.currentUser?.uid

        holder.binding.title.text = article.title
        holder.binding.description.text = article.description
        Glide
            .with(holder.binding.imageview.context)
            .load(article.urlToImage)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.imageview)

        val url = article.url

        // Set initial favorite icon based on the current state
        userId?.let {
            val favoritesRef = firestore.collection("users")
                .document(it)
                .collection("favorites")
                .document(articleId)

            favoritesRef.get().addOnSuccessListener { document ->
                toggleFavoriteIcon(holder.binding.favBtn, document.exists())
            }.addOnFailureListener { e ->
                Log.e("Firestore", "Error checking favorite status", e)
                toggleFavoriteIcon(holder.binding.favBtn, false) // Default to not favorited
            }
        } ?: run {
            toggleFavoriteIcon(
                holder.binding.favBtn,
                false
            ) // Default to not favorited if not authenticated
        }

        holder.binding.articleContainer.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            activity.startActivity(intent)
        }

        holder.binding.shareBtn.setOnClickListener {
            ShareCompat
                .IntentBuilder(activity)
                .setType("text/plain")
                .setChooserTitle("Share this article")
                .setText(url)
                .startChooser()
        }

        holder.binding.favBtn.setOnClickListener {
            userId?.let {
                val favoritesRef = firestore.collection("users")
                    .document(it)
                    .collection("favorites")
                    .document(articleId)

                favoritesRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Article is already favorited, remove it
                        favoritesRef.delete().addOnSuccessListener {
                            Log.d("Firestore", "Article removed from favorites successfully")
                            toggleFavoriteIcon(holder.binding.favBtn, false)
                        }.addOnFailureListener { e ->
                            Log.e("Firestore", "Error removing article from favorites", e)
                        }
                    } else {
                        // Article is not favorited, add it
                        addArticleToFavorites(article, articleId)
                        toggleFavoriteIcon(holder.binding.favBtn, true)
                    }
                }.addOnFailureListener { e ->
                    Log.e("Firestore", "Error checking favorite status", e)
                }
            } ?: run {
                Log.e("Firestore", "User not authenticated")
            }
        }
    }

    private fun addArticleToFavorites(article: Article, articleId: String) {
        val userId = auth.currentUser?.uid
        userId?.let {
            val favoritesRef = firestore.collection("users")
                .document(it)
                .collection("favorites")

            val articleRef = favoritesRef.document(articleId)
            articleRef.set(
                mapOf(
                    "title" to article.title,
                    "url" to article.url,
                    "urlToImage" to article.urlToImage,
                    "description" to article.description,
                    "id" to articleId
                )
            ).addOnSuccessListener {
                Log.d("Firestore", "Article added to favorites successfully")
            }.addOnFailureListener { e ->
                Log.e("Firestore", "Error adding article to favorites", e)
            }
        } ?: run {
            Log.e("Firestore", "User not authenticated")
        }
    }

    private fun generateIdFromUrl(url: String): String {
        return Base64.getUrlEncoder().encodeToString(url.toByteArray())
            .replace("=", "") // Remove padding to keep the ID clean
    }

    private fun toggleFavoriteIcon(imageView: ImageView, isFavorited: Boolean) {
        val icon = if (isFavorited) R.drawable.favorite else R.drawable.favorite_border
        imageView.setImageResource(icon)
    }
}
