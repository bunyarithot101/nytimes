package com.example.nytimes.serach

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.SearchEvent
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
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nytimes.R
import com.example.nytimes.list.ListFragmentDirections
import com.example.nytimes.list.resultsList

class serachAdapter(
        private var context: Context,
        private var data: List<resultsList>,
): RecyclerView.Adapter<serachAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.serach_card_title)
        val image: ImageView = view.findViewById(R.id.serach_card_image)
        val description: TextView = view.findViewById(R.id.serach_card_desc)
        val writedby: TextView = view.findViewById(R.id.serach_card_writedby)

        val onload: ProgressBar = view.findViewById(R.id.serach_card_onload)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): serachAdapter.ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.serach_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 600
        view.startAnimation(anim)
    }

    override fun onBindViewHolder(holder: serachAdapter.ItemViewHolder, position: Int) {
        setFadeAnimation(holder.itemView)

        val items = data[position]

        holder.title.text =  items.title

        val fetchPng = items.multimedia.filter {
//            it.format == "superJumbo"
            it.height >= it.width
        }.first().url

//        thumbLarge

        Glide.with(context)
            .load(fetchPng)
            .dontAnimate()
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
            .override(100, 100)
            .into(holder.image)


        holder.description.text = items.abstract
        holder.writedby.text = items.byline

        holder.itemView.setOnClickListener {
            val action = SerachFragmentDirections.actionSerachFragmentToDetailFragment(items)
            holder.itemView.findNavController().navigate(action)
        }




    }

    override fun getItemCount(): Int {
        return data.size
    }


}