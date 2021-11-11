package com.example.identify.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.HomePageFragmentPath
import com.example.base.QuestionFragmentPath
import com.example.identify.R
import com.example.identify.adapter.ViewPagerAdapter
import com.example.identify.model.NetServiceManager
import com.example.identify.viewmodel.QuestionViewModel
import java.io.File

@Route(path = HomePageFragmentPath)
class HomePageFragment() : Fragment(), View.OnClickListener {
    @JvmField
    @Autowired
    var frameId: Int? = null
    private val takePhoto = 1
    private val fromAlbum = 2
    private val viewModel: QuestionViewModel by viewModels()
    private lateinit var imageUri: Uri
    private lateinit var outputImg: File

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

    //加载中
    private lateinit var loadingView: ProgressBar

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
        loadingView = view.findViewById(R.id.loading_view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ARouter.getInstance().inject(this)
        question.setOnClickListener(this)
        photoRecognition.setOnClickListener(this)
        initImageLists()
        initViewPagerBanner()
        initIndicator()
        initTakePhoto()
    }

    //拍照识别
    private fun initTakePhoto() {
        takePicture.setOnClickListener {
            outputImg = File(requireContext().externalCacheDir, "output_img.jpg")
            if (outputImg.exists()) {
                outputImg.delete()
            }
            outputImg.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.identify.fragment.fileprovider",
                    outputImg
                )
            } else {
                Uri.fromFile(outputImg)
            }
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                startLoading()
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(
                        requireActivity().contentResolver.openInputStream(imageUri)
                    )
                    viewModel.getImageBase64(bitmap)
                    getImageData()
                }
            }
            fromAlbum -> {
                startLoading()
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val bitmap = data.data?.let {
                        requireActivity().contentResolver.openFileDescriptor(it, "r")?.use { par ->
                            BitmapFactory.decodeFileDescriptor(par.fileDescriptor)
                        }
                    }
                    bitmap?.let { viewModel.getImageBase64(it) }
                    getImageData()
                }
            }
        }
    }

    //获取图片的base64编码以及解析结果
    private fun getImageData() {
        viewModel.base64.observe(requireActivity()) {
            if (it.length < 20) {
                stopLoading()
                Toast.makeText(requireContext(), "图片识别失败", Toast.LENGTH_LONG).show()
            } else {
                NetServiceManager(viewModel).requestImageResult(it)
            }
        }
        viewModel.resultLists.observe(requireActivity()) {
            stopLoading()
            val fragment = ImageRecognitionResultFragment(it)
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null).replace(frameId!!, fragment).commit()
        }
    }

    //    初始化指示点
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

    //    跳转进答题页面
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ask_question -> {
                val fragmentInstance =
                    ARouter.getInstance().build(QuestionFragmentPath).navigation() as Fragment
                requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(frameId!!, fragmentInstance).commit()

            }
            R.id.picture_recognition -> {
                initPictureRecognition()
            }
        }
    }

    //照片识别
    private fun initPictureRecognition() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, fromAlbum)
    }

    private fun startLoading() {
        if (loadingView.visibility == View.GONE) {
            loadingView.visibility = View.VISIBLE
        }
    }

    private fun stopLoading() {
        if (loadingView.visibility == View.VISIBLE) {
            loadingView.visibility = View.GONE
        }
    }
}
