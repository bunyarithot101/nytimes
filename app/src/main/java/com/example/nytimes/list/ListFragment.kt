package com.example.nytimes.list

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytimes.R
import com.example.nytimes.databinding.FragmentListBinding
import com.example.nytimes.handle.networkStatusModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber


private var _binding: FragmentListBinding? = null
private val binding get() = _binding!!

class ListFragment : Fragment() {

    private lateinit var networkStatus: networkStatusModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list, container, false)

        _binding = FragmentListBinding.inflate(inflater, container, false)

        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        networkStatus = ViewModelProvider(requireActivity()).get(networkStatusModel::class.java)

        networkStatus.status.observe(viewLifecycleOwner, Observer { status ->
             if(status == false){
                 showDialog()
             }
        })

        callAPi()

        val btn = requireActivity().findViewById<Button>(R.id.btn_serach)

        btn.setOnClickListener {
            val navController = findNavController()
            val destination = navController.currentDestination as FragmentNavigator.Destination
            if (javaClass.name == destination.className) {
                navController.navigate(R.id.action_listFragment_to_serachFragment)
            }

        }

        requireActivity().findViewById<Button>(R.id.btn_serach).isVisible = true

    }

    private fun callAPi(){

        //        books.json?api-key=GDrQ2aBDKGj6DELALg9H4JeXLysN1peW
        val nytApi = nyTimesApi.retrofitService.getBooks("GDrQ2aBDKGj6DELALg9H4JeXLysN1peW")

        nytApi?.enqueue(object : retrofit2.Callback<list?> {

            override fun onResponse(
                call: retrofit2.Call<list?>,
                response: retrofit2.Response<list?>
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

        val linearLayoutManager2nd = GridLayoutManager(
            context,
            2,
            LinearLayoutManager.VERTICAL,
            false
        )


        val recyclerView = binding.listRecyclerView
        val itemArrayAdapter2nd = listAdapter(requireContext(), data)
        recyclerView.setLayoutManager(linearLayoutManager2nd)
        recyclerView.setAdapter(itemArrayAdapter2nd)

    }

    fun handleError() {
        val navController = findNavController()
        val destination = navController.currentDestination as FragmentNavigator.Destination
        if (javaClass.name == destination.className) {
            navController.navigate(
                    R.id.action_listFragment_to_errorFragment
            )
        }
    }


    fun showDialog(){
        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme_Center)
            .setTitle(resources.getString(R.string.status_off))
            .show()
    }





}