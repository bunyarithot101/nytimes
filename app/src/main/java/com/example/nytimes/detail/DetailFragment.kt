package com.example.nytimes.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nytimes.R
import com.example.nytimes.databinding.FragmentDetailBinding
import com.example.nytimes.handle.networkStatusModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber


private var _binding: FragmentDetailBinding? = null
private val binding get() = _binding!!


class DetailFragment : Fragment() {

    private lateinit var networkStatus: networkStatusModel

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val view = binding.root


        networkStatus = ViewModelProvider(requireActivity()).get(networkStatusModel::class.java)

        networkStatus.status.observe(viewLifecycleOwner, Observer { status ->
            if(status == false){
                showDialog()
            }
        })

        setFadeAnimation(view)

        binding.detailTitle.text = args.result.title
        binding.detailAbst.text = args.result.abstract

        val fetchPng =  args.result.multimedia.filter {
            it.format == "superJumbo"
        }.first().url

        Glide.with(this)
                .load(fetchPng)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean,
                    ): Boolean {

                        binding.detailOnload.isVisible = true
                        binding.detailImage.isVisible = false
//                        holder.onload.isVisible = true

                        return false
                    }

                    override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean,
                    ): Boolean {
//                        holder.onload.isVisible = false

                        binding.detailOnload.isVisible = false
                        binding.detailImage.isVisible = true

                        return false
                    }

                })
                .into(binding.detailImage)


       binding.btnDetail.setOnClickListener {
           val uri: Uri = Uri.parse(args.result.url)

           val intent = Intent(Intent.ACTION_VIEW, uri)
           startActivity(intent)
       }

        requireActivity().findViewById<Button>(R.id.btn_serach).isVisible = false


        return view
    }


    fun showDialog(){
        val items = arrayOf("Item 1", "Item 2", "Item 3")

        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme_Center)
            .setTitle(resources.getString(R.string.status_off))
//                     .setNeutralButton("Cancel") { dialog, which ->
//                         // Respond to neutral button press
//                     }
//                     .setPositiveButton("OK") { dialog, which ->
//                         // Respond to positive button press
//                     }
            // Single-choice items (initialized with checked item)
//                     .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
//                         // Respond to item chosen
//                     }
            .show()
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 2000
        view.startAnimation(anim)
    }



}