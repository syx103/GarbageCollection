package com.example.identify

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import java.lang.StringBuilder
import kotlin.experimental.and


class HomePageFragment : Fragment() {
    //轮播图
    private lateinit var viewPagerBanner: ViewPager2

    //轮播图上面的指示点布局
    lateinit var linearLayout: LinearLayout

    //    搜索框：进行垃圾搜索
    private lateinit var searchGarbage: TextView

    //    语音识别
    private lateinit var voiceRecognition: LinearLayout

    //    拍照识别
    private lateinit var takePicture: LinearLayout

    //    图片识别
    private lateinit var photoRecognition: LinearLayout

    //    测试题
    private lateinit var question: TextView

    //    可回收垃圾
    private lateinit var recyclable: ImageFilterView

    //    有害垃圾
    private lateinit var hazardous: ImageFilterView

    //    湿垃圾
    private lateinit var householdFood: ImageFilterView

    //    干垃圾
    private lateinit var residual: ImageFilterView


    private val VOICE_MODEL = 1234

    var lastPosition: Int? = null
    private val handler = Handler(Looper.getMainLooper())
    private var imageLists = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = Color.parseColor("#79CDCD")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        viewPagerBanner = view.findViewById(R.id.viewpager_banner)
        linearLayout = view.findViewById(R.id.indicator_container)
        searchGarbage = view.findViewById(R.id.search_button)
        voiceRecognition = view.findViewById(R.id.voice_recognition)
        takePicture = view.findViewById(R.id.take_photo_recognition)
        photoRecognition = view.findViewById(R.id.picture_recognition)
        question = view.findViewById(R.id.ask_question)
        recyclable = view.findViewById(R.id.recyclable)
        hazardous = view.findViewById(R.id.hazardous)
        householdFood = view.findViewById(R.id.household_food)
        residual = view.findViewById(R.id.residual)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initImageLists()
        initViewPagerBanner()
        initIndicator()
        initClick()
    }

    fun initClick(){
        voiceRecognition.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"开始语音")
            startActivityForResult(intent,VOICE_MODEL);
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VOICE_MODEL && resultCode == RESULT_OK) {
            if (data == null) {
                return
            }
            val results = data.getStringArrayListExtra (RecognizerIntent.EXTRA_RESULTS) ?: return
            var tempResults = StringBuilder()
            for (i in results) {
                tempResults.append(i)
            }
            val result = tempResults.toString()
            //跳转
        }
    }

    private fun initIndicator() {
        val length = imageLists.size - 1
        (0..length).forEach { index ->
            val imageView = ImageView(context)
            if (index == 0) {
                imageView.setBackgroundResource(R.drawable.indicator_selected)
            } else {
                imageView.setBackgroundResource(R.drawable.indicator_not_selected)
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.marginEnd = 14
            imageView.layoutParams = params
            linearLayout.addView(imageView)
        }
    }

    /*
    * ViewPager适配器，设置轮播的位置
    * */
    private fun initViewPagerBanner() {
        val adapter = ViewPagerAdapter(imageLists)
        viewPagerBanner.adapter = adapter
        viewPagerBanner.currentItem = 500
        lastPosition = 500
        viewPagerBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //轮播时改变指示点
                val current = position % 4
                val last = lastPosition!! % 4
                linearLayout.getChildAt(current)
                    .setBackgroundResource(R.drawable.indicator_selected)
                linearLayout.getChildAt(last)
                    .setBackgroundResource(R.drawable.indicator_not_selected)
                lastPosition = position
            }
        })
    }

    /*
    * 初始化轮播的图片列表
    * */
    private fun initImageLists() {
        imageLists.add(R.drawable.garbage_image_one)
        imageLists.add(R.drawable.garbage_image_two)
        imageLists.add(R.drawable.garbage_image_three)
        imageLists.add(R.drawable.garbage_image_four)

    }

    //自动轮播
    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 7000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(runnable)
    }


    /*
    * 循环调用，让轮播图每隔5S自己滑动
    * */
    private var runnable: Runnable = Runnable {
        var currentPosition = viewPagerBanner.currentItem
        currentPosition++
        viewPagerBanner.setCurrentItem(currentPosition, true)
        handler.postDelayed(runnable2, 7000)
    }

    private val runnable2: Runnable = Runnable {
        var currentPosition = viewPagerBanner.currentItem
        currentPosition++
        viewPagerBanner.setCurrentItem(currentPosition, true)
        handler.postDelayed(runnable, 7000)
    }
}
