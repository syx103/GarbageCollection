package com.example.garbagecollection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewViewModel :ViewModel() {
    var phoneNumber : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")
    var passwordSure : MutableLiveData<String> = MutableLiveData("")
}