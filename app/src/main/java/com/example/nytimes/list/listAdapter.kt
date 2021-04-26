package com.example.nytimes.list

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nytimes.R
import timber.log.Timber

class listAdapter(
    private var context: Context,
    private var data: List<resultsList>,
) : RecyclerView.Adapter<listAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView = view.findViewById(R.id.item_image)
        val description: TextView = view.findViewById(R.id.item_description)

        val onload: ProgressBar = view.findViewById(R.id.item_onload)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listAdapter.ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 600
        view.startAnimation(anim)
    }

    override fun onBindViewHolder(holder: listAdapter.ItemViewHolder, position: Int) {
        setFadeAnimation(holder.itemView)


        val items = data[position]

//        val fetchPng = items.multimedia.filter {
//            it.format == "superJumbo"
//        }.first().url

        val fetchPng = items.multimedia.first().url

        Glide.with(context.applicationContext)
            .load(fetchPng)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .override(200, 300)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                ): Boolean {
                    holder.onload.isVisible = true

                    return false
                }

                override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean,
                ): Boolean {
                    holder.onload.isVisible = false

                    return false
                }


            })
            .into(holder.image)


        holder.description.text = items.title

        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(items)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {

        return data.size
    }

}