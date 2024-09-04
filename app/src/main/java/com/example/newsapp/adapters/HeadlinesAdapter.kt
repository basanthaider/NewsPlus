package com.example.newsapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.R
import com.example.newsapp.databinding.ArticleListItemBinding
import com.example.newsapp.models.Article

class HeadlinesAdapter(val activity: Activity, val articles: ArrayList<Article>) :
    Adapter<HeadlinesAdapter.HeadlinesViewHolder>() {

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
        Log.d("trace", "Link:${articles[position].urlToImage}")

        holder.binding.title.text = articles[position].title

        holder.binding.description.text = articles[position].description
        Glide
            .with(holder.binding.imageview.context)
            .load(articles[position].urlToImage)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.imageview)

        val url = articles[position].url

        holder.binding.articleContainer.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, url.toUri())
            activity.startActivity(i)
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
        }

    }


}