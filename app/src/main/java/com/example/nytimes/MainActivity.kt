package com.example.nytimes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.*
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.nytimes.handle.networkStatusFragment
import com.example.nytimes.handle.networkStatusModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var networkStatus: networkStatusModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

        setSupportActionBar(findViewById(R.id.toolbar))
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))


        SetStatusNetworkInModel()

    }

    private fun SetStatusNetworkInModel(){
        networkStatus = ViewModelProvider(this).get(networkStatusModel::class.java)
        networkStatus.setNetworkStatus(isNetworkAvailable())
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        SetStatusNetworkInModel()

        return super.onSupportNavigateUp()|| navController.navigateUp()
    }


    override fun onResume() {
        SetStatusNetworkInModel()
        super.onResume()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)

        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }






}