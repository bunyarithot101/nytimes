package com.example.nytimes.serach

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytimes.R
import com.example.nytimes.databinding.FragmentSerachBinding
import com.example.nytimes.handle.networkStatusModel
import com.example.nytimes.list.list
import com.example.nytimes.list.nyTimesApi
import com.example.nytimes.list.resultsList
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber


private var _binding: FragmentSerachBinding? = null
private val binding get() = _binding!!


class SerachFragment : Fragment() {

    private lateinit var networkStatus: networkStatusModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_serach, container, false)

        _binding = FragmentSerachBinding.inflate(inflater, container, false)

        val view = binding.root

        networkStatus = ViewModelProvider(requireActivity()).get(networkStatusModel::class.java)

        networkStatus.status.observe(viewLifecycleOwner, Observer { status ->
            if(status == false){
                showDialog()
            }
        })

        requireActivity().findViewById<Button>(R.id.btn_serach).isVisible = false

        callAPi()

        return view
    }

    private fun  callAPi(){

        val nytApi = nyTimesApi.retrofitService.getBooks("GDrQ2aBDKGj6DELALg9H4JeXLysN1peW")

        nytApi?.enqueue(object : retrofit2.Callback<list?> {

            override fun onResponse(
                call: retrofit2.Call<list?>,
                response: retrofit2.Response<list?>,
            ) {

                if (!response.isSuccessful) {
                    handleError()
                }
                val data = response.body()!!
                callRecyclerView(data.results)

            }

            override fun onFailure(call: retrofit2.Call<list?>, t: Throwable) {
                handleError()
            }
        })

    }

    private fun callRecyclerView(data: List<resultsList>) {

        val adpter = serachAdapter(requireContext(), data)

        val recyclerView = binding.searchRecyclerview
        recyclerView.adapter = adpter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        onSeraching(data)
    }

    private fun onSeraching(data: List<resultsList>) {


        binding.serachEditSerach.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Timber.i("ASFASFASFASFASF beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val txt = s

                val output = data.filter {
                    it.title.toLowerCase().contains(s.toString().toLowerCase())
                }

                if (txt!!.length > 0) {

                    val adpter = serachAdapter(requireContext(), output)

                    val recyclerView = binding.searchRecyclerview
                    recyclerView.adapter = adpter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())

                } else {
                    callRecyclerView(data)
                }

            }

            override fun afterTextChanged(s: Editable?) {
//                Timber.i("ASFASFASFASFASF afterTextChanged")
            }
        })

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

    fun handleError() {
        val navController = findNavController()
        val destination = navController.currentDestination as FragmentNavigator.Destination
        if (javaClass.name == destination.className) {
            navController.navigate(
                R.id.action_serachFragment_to_errorFragment
            )
        }
    }


}