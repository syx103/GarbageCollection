package com.example.identify.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.identify.QuestionBean
import com.example.identify.model.DataList
import com.example.identify.model.NetServiceManager
import com.example.identify.model.ResultNewsList

class QuestionViewModel : ViewModel() {
    //    当前的答题页
    private val _currentIndex: MutableLiveData<Int> = MutableLiveData(0)
    val currentIndex: LiveData<Int>
        get() = _currentIndex
    private val _questionPageLists: MutableLiveData<MutableList<QuestionBean>> = MutableLiveData()

    // 展示页面的数据
    val questionPageLists: LiveData<MutableList<QuestionBean>>
        get() = _questionPageLists
    private val _resultLists: MutableLiveData<MutableList<DataList>> = MutableLiveData()
    val resultLists: LiveData<MutableList<DataList>>
        get() = _resultLists

    private val _base64: MutableLiveData<String> = MutableLiveData()
    val base64: LiveData<String>
        get() = _base64

    fun updateIndex(index: Int) {
        _currentIndex.value = index
    }

    fun updateQuestionPageList(questionBean: QuestionBean) {
        var list = _questionPageLists.value
        if (list == null) {
            list = mutableListOf()
        }
        list.add(questionBean)
        _questionPageLists.value = list
    }

    fun updateResultLists(content: MutableList<DataList>) {
        _resultLists.value = content
    }

    fun updateBase64(base64: String){
        _base64.value = base64
    }

   /* fun getImageBase64(imageUri: String) {
        NetServiceManager(this).imageBase64(imageUri)
    }*/

    @RequiresApi(Build.VERSION_CODES.N)
    fun getImageBase64(bitmap: Bitmap) {
        NetServiceManager(this).imageBase64(bitmap)
    }
}