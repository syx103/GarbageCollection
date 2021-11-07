package com.example.knowledgedisplay

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobQuery
import com.example.base.BaseActivity
import java.util.*
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener

import cn.bmob.v3.listener.SaveListener
import kotlin.collections.ArrayList
import cn.bmob.v3.listener.QueryListener
import kotlin.math.log


class KnowledgeDisplayActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_display)
        initBmobData()
    }

    // 初始化Bmob数据
    private fun initBmobData() {
        Bmob.initialize(this,"1a2ac0beae777b1707f89baece5bc014")
        //addData()
        getData()
    }

    // 获取数据
    private fun getData() {
        val knowledgeQuery : BmobQuery<KnowledgeObject> = BmobQuery<KnowledgeObject>()
        knowledgeQuery.findObjects(object : FindListener<KnowledgeObject>() {
            override fun done(knowledgeList: MutableList<KnowledgeObject>?, e: BmobException?) {
                if(e == null) {
                    // TODO: 2021/11/7 recyclerView的数据处理
                } else {
                    Toast.makeText(this@KnowledgeDisplayActivity, "获取数据失败：${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("BMOB","$e.message")
                }
            }
        })

    }

    // 添加数据
    private fun addData() {
        val knowledgeList = ArrayList<KnowledgeObject>()
        knowledgeList.add(KnowledgeObject("垃圾分类基础知识，垃圾分类科普体验馆","https://zhuanlan.zhihu.com/p/411770170"))
        knowledgeList.add(KnowledgeObject("为什么要进行垃圾分类？","http://www.xinhuanet.com/science/2018-10/30/c_137566807.htm"))
        knowledgeList.add(KnowledgeObject("垃圾分类知识科普，这些你都知道吗？","https://new.qq.com/omn/20210819/20210819A0969M00.html"))
        knowledgeList.add(KnowledgeObject("干货分享：垃圾分类科普！看完就懂","https://www.bilibili.com/video/BV1Xi4y1x784/"))
        knowledgeList.add(KnowledgeObject("垃圾分类处理技巧？垃圾分类科普小知识","https://zhuanlan.zhihu.com/p/88980532"))
        knowledgeList.add(KnowledgeObject("垃圾分类有历史：且看古人的智慧","https://kepu.gmw.cn/2019-12/03/content_33369949.htm"))
        knowledgeList.add(KnowledgeObject("垃圾分类的前世今生","https://www.bilibili.com/read/cv6927398/"))
        knowledgeList.add(KnowledgeObject("尤雾：日本垃圾分类的起源","https://www.douban.com/note/725835229/"))
        knowledgeList.add(KnowledgeObject("为什么全国都要实行垃圾分类，背后的原因是什么？","https://www.zhihu.com/question/333219434"))
        knowledgeList.add(KnowledgeObject("垃圾分类三大原则","https://www.yebaike.com/21/202007/443001.html"))
        val count:Int = knowledgeList.size
        for (i in 0 until count) {
            knowledgeList[i].save(object : SaveListener<String>() {
                override fun done(objectId: String?, e: BmobException?) {
                    if (e == null) {
                        Toast.makeText(this@KnowledgeDisplayActivity, "添加数据失败", Toast.LENGTH_SHORT).show()
                        Log.d("BMOB","添加成功")
                    } else {
                        Toast.makeText(this@KnowledgeDisplayActivity, "添加失败，失败信息：${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}