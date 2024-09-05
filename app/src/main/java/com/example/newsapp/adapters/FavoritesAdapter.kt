package com.example.newsapp.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.R
import com.example.newsapp.databinding.ArticleListItemBinding
import com.example.newsapp.models.Article
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesAdapter(
    private val activity: FragmentActivity,
    private val articles: ArrayList<Article>,
    private val onFavoriteRemoved: (Article) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    class FavoritesViewHolder(val binding: ArticleListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding =
            ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val article = articles[position]

        holder.binding.apply {
            title.text = article.title
            description.text = article.description
            Glide.with(imageview.context)
                .load(article.urlToImage)
                .error(R.drawable.broken_image)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(imageview)

            articleContainer.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, article.url.toUri())
                activity.startActivity(intent)
            }

            shareBtn.setOnClickListener {
                ShareCompat.IntentBuilder(activity)
                    .setType("text/plain")
                    .setChooserTitle("Share this article")
                    .setText(article.url)
                    .startChooser()
            }

            favBtn.setImageResource(R.drawable.favorite)  // Always show as favorited
            favBtn.setOnClickListener {
                removeFromFavorites(article)
            }
        }
    }

    private fun removeFromFavorites(article: Article) {
        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            firestore.collection("users")
                .document(uid)
                .collection("favorites")
                .document(article.id ?: "")
                .delete()
                .addOnSuccessListener {
                    onFavoriteRemoved(article)
                }
        }
    }
}