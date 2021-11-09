package com.example.knowledgedisplay

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.BaseActivity
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class KnowledgeDisplayActivity : BaseActivity() {

    private val TAG = "垃圾分类"
    private var recyclerView: RecyclerView? = null
    private var newsAdapter: NewsAdapter? = null
    private var smartRefreshLayout: SmartRefreshLayout? = null
    private var newsListCache: ArrayList<NewsListBean> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_display)
        initView()
        fetchData()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.rv_knowledge_display)
        smartRefreshLayout = findViewById(R.id.srl)
        smartRefreshLayout?.setOnLoadMoreListener {
            fetchData()
        }
    }

    @SuppressLint("CheckResult")
    private fun fetchData() {
        KnowledgeDisplayNetwork.fetchNewsData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.code == 200) {
                    parseData(it)
                } else {
                    Toast.makeText(this, "网络请求错误", Toast.LENGTH_SHORT).show()
                    when (it.code) {
                        110 -> Log.d(TAG, throw RuntimeException("接口暂时维护中"))
                        150 -> Log.d(TAG, throw RuntimeException("接口请求次数不足"))
                        230 -> Log.d(TAG, throw RuntimeException("参数key错误"))
                    }
                }
            }, {
                Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show()
                Log.d(TAG, throw RuntimeException("网络请求失败"))
            })
    }

    private fun parseData(data: NewsItemBean) {
        newsListCache.addAll(data.newslist)
        if (newsAdapter == null) {
            newsAdapter = NewsAdapter(data.newslist)
            recyclerView?.let {
                it.adapter = newsAdapter
                it.layoutManager = LinearLayoutManager(this)
            }
        } else {
            newsAdapter?.refreshListData(newsListCache)
        }
    }

/*    // 初始化Bmob数据
    private fun initBmobData() {
        Bmob.initialize(this, "1a2ac0beae777b1707f89baece5bc014")
        //addData()
        getData()
    }

    // 获取数据
    private fun getData() {
        val knowledgeQuery: BmobQuery<KnowledgeObject> = BmobQuery<KnowledgeObject>()
        knowledgeQuery.findObjects(object : FindListener<KnowledgeObject>() {
            override fun done(knowledgeList: MutableList<KnowledgeObject>?, e: BmobException?) {
                if (e == null) {
                    // TODO: 2021/11/7 recyclerView的数据处理
                } else {
                    Toast.makeText(
                        this@KnowledgeDisplayActivity,
                        "获取数据失败：${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("BMOB", "$e.message")
                }
            }
        })

    }

    // 添加数据
    private fun addData() {
        val knowledgeList = ArrayList<KnowledgeObject>()
        knowledgeList.add(
            KnowledgeObject(
                "垃圾分类基础知识，垃圾分类科普体验馆",
                "https://zhuanlan.zhihu.com/p/411770170"
            )
        )
        knowledgeList.add(
            KnowledgeObject(
                "为什么要进行垃圾分类？",
                "http://www.xinhuanet.com/science/2018-10/30/c_137566807.htm"
            )
        )
        knowledgeList.add(
            KnowledgeObject(
                "垃圾分类知识科普，这些你都知道吗？",
                "https://new.qq.com/omn/20210819/20210819A0969M00.html"
            )
        )
        knowledgeList.add(
            KnowledgeObject(
                "干货分享：垃圾分类科普！看完就懂",
                "https://www.bilibili.com/video/BV1Xi4y1x784/"
            )
        )
        knowledgeList.add(
            KnowledgeObject(
                "垃圾分类处理技巧？垃圾分类科普小知识",
                "https://zhuanlan.zhihu.com/p/88980532"
            )
        )
        knowledgeList.add(
            KnowledgeObject(
                "垃圾分类有历史：且看古人的智慧",
                "https://kepu.gmw.cn/2019-12/03/content_33369949.htm"
            )
        )
        knowledgeList.add(KnowledgeObject("垃圾分类的前世今生", "https://www.bilibili.com/read/cv6927398/"))
        knowledgeList.add(KnowledgeObject("尤雾：日本垃圾分类的起源", "https://www.douban.com/note/725835229/"))
        knowledgeList.add(
            KnowledgeObject(
                "为什么全国都要实行垃圾分类，背后的原因是什么？",
                "https://www.zhihu.com/question/333219434"
            )
        )
        knowledgeList.add(
            KnowledgeObject(
                "垃圾分类三大原则",
                "https://www.yebaike.com/21/202007/443001.html"
            )
        )
        val count: Int = knowledgeList.size
        for (i in 0 until count) {
            knowledgeList[i].save(object : SaveListener<String>() {
                override fun done(objectId: String?, e: BmobException?) {
                    if (e == null) {
                        Toast.makeText(this@KnowledgeDisplayActivity, "添加数据失败", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("BMOB", "添加成功")
                    } else {
                        Toast.makeText(
                            this@KnowledgeDisplayActivity,
                            "添加失败，失败信息：${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }*/
}