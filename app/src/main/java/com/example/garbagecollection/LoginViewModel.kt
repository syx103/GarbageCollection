package com.example.garbagecollection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel :ViewModel() {
    var phoneNumber :MutableLiveData<String> = MutableLiveData("")
    var password :MutableLiveData<String> = MutableLiveData("")
}