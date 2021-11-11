package com.example.identify.model

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.identify.*
import com.example.identify.viewmodel.QuestionViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.net.URLEncoder


class NetServiceManager(private val viewModel: QuestionViewModel) {

    private lateinit var questionSpecificPage: QuestionBean
    private fun retrofitBuild(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val myService = retrofitBuild(questionUrl).create(MyService::class.java)
    private val imageResultService = retrofitBuild(imageUrl).create(ImageResultService::class.java)
    private val imageBase64Service = retrofitBuild(base64Url).create(ImageBase64Service::class.java)

    @SuppressLint("CheckResult")
    fun requestNetwork() {
        myService.questionPage(key)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    questionSpecificPage = it
                    viewModel.updateQuestionPageList(it)
                    Log.e("NetService", "success:$it,${viewModel.questionPageLists.value?.size}")
                },
                {
                    Log.e("NetService", "error:$it")
                })
    }

    @SuppressLint("CheckResult")
    fun requestImageResult(imageBase64: String) {
        imageResultService.imageRecognitionResult(URLEncoder.encode(imageBase64,"UTF-8"),
            "APPCODE $appcode")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {
                    viewModel.updateResultLists(it.data[0].list)
                    Log.e("NetService","requestImageResult: ${it.data[0].list}")
                },
                {
                    Log.e("NetService", "imageResult:$it")
                }
            )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun imageBase64(bitmap: Bitmap){
        // 将Bitmap转换成字符串
        var string: String? = null
        val bStream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.JPEG, 40, bStream)
        val bytes: ByteArray = bStream.toByteArray()
        string = Base64.encodeToString(bytes, Base64.NO_WRAP)
        Log.e("NetService", "Base64:$string")
        viewModel.updateBase64(string)
    }
}
