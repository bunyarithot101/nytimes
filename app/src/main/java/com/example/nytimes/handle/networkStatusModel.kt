package com.example.nytimes.handle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class networkStatusModel: ViewModel() {
    var status = MutableLiveData<Boolean>()
//    var numberPokemonOnDetailList = MutableLiveData<Any>()

    fun setNetworkStatus(txt: Boolean){
        this.status.setValue(txt)
    }
}

