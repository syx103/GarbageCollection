package com.example.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InformationViewModel :ViewModel() {
    var name :MutableLiveData<String> = MutableLiveData()
    var content :MutableLiveData<String> = MutableLiveData()
    var sex :MutableLiveData<String> = MutableLiveData()
}