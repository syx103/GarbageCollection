package com.example.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel :ViewModel() {
    var myName : MutableLiveData<String> = MutableLiveData()
    var myContent : MutableLiveData<String> = MutableLiveData()
    var mySex : MutableLiveData<String> = MutableLiveData()

    var myHot :MutableLiveData<List<ItemModel>>? = null

}