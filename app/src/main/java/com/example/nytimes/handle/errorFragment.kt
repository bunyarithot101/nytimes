package com.example.nytimes.handle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nytimes.R


private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [errorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class errorFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

}