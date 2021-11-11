package com.example.login

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.BaseActivity
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class LoginActivity : BaseActivity() {

    private lateinit var myImageView: ImageView
    private lateinit var myContinue : TextView
    private lateinit var myChangeInformation : TextView
    private lateinit var myHotList : RecyclerView
    private val fromAlbum = 2
    private lateinit var myContentName: TextView
    private lateinit var myContent:TextView
    private lateinit var mySex :TextView
    private lateinit var myAge : TextView
    private var hotList :List<ItemModel>? = LinkedList<ItemModel>()

    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        initView()
        initList()
        val layoutManager = LinearLayoutManager(this)
        myHotList.layoutManager = layoutManager
        myHotList.adapter = HotAdapter(myViewModel.myHot?.value!!)
    }

    override fun onResume() {
        super.onResume()
        //每次进入需要在登录中记录的sp中取出数据
        val preferences = getSharedPreferences("data", MODE_PRIVATE)
        val name = preferences.getString("name","")
        val content = preferences.getString("content","")
        val sex = preferences.getString("sex","")

        myViewModel.myHot = MutableLiveData(hotList)
        myViewModel.myContent = MutableLiveData(content)
        myViewModel.myName = MutableLiveData(name)
        myViewModel.mySex = MutableLiveData(sex)

    }

    private fun initList() {
        //加载排行榜数据
        val retrofit = Retrofit.Builder().baseUrl("http://api.tianapi.com/hotlajifenlei/index")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val hotRequest = retrofit.create(HotRequest::class.java)
        val call = hotRequest.getList()
        call.enqueue(object :Callback<HotResponse> {
            override fun onResponse(call: Call<HotResponse>, response: Response<HotResponse>) {

                val result = response.body()
                if (result != null) {
                    hotList = result.model
                    myViewModel.myHot = MutableLiveData(hotList)
                }
            }

            override fun onFailure(call: Call<HotResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"请求失败",Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun initView(){

        myImageView = findViewById(R.id.my_image)
        myContinue = findViewById(R.id.my_continue)
        myChangeInformation = findViewById(R.id.my_change_information)
        myHotList = findViewById(R.id.hot_list)

        myContentName = findViewById(R.id.my_content_name)
        myContent = findViewById(R.id.my_content)
        mySex = findViewById(R.id.my_sex)
        myAge = findViewById(R.id.my_age)


        myContentName.text = myViewModel.myName.toString()
        myContent.text = myViewModel.myContent.toString()
        mySex.text = myViewModel.mySex.toString()

        //打开相册选取图片
        myImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent,fromAlbum)
        }

        myContinue.setOnClickListener {
            //继续答题跳转页面

        }
        myChangeInformation.setOnClickListener {
            val intent = Intent(this,ChangeInformationActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            fromAlbum -> if (resultCode == Activity.RESULT_OK && data != null) {
                data.data?.let { uri ->
                    val bitmap = getBitmapFromUri(uri)
                    myImageView.setImageBitmap(bitmap)
                    Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT)
                    //将选中的图片保存进sp中

                }
            }
        }
    }

    private fun getBitmapFromUri(uri : Uri) = contentResolver.openFileDescriptor(uri,"r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }
}