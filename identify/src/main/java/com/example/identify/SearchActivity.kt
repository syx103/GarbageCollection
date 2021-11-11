package com.example.identify

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.BaseActivity
import com.example.utils.isEmpty
import com.example.utils.setTextViewText
import com.example.utils.setViewVisibility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchActivity : BaseActivity() {

    private var tvTitle: TextView? = null
    private var ivBack: ImageView? = null
    private var vDivider: View? = null
    private var etSearchWord: EditText? = null
    private var ivClear: ImageView? = null
    private var llHotSearch: LinearLayout? = null
    private var rvSearchResult: RecyclerView? = null
    private var rvHotSearch: RecyclerView? = null
    private var hotWordsAdapter: HotWordsAdapter? = null
    private var searchResultAdapter: SearchResultAdapter? = null
    private var garbageName: String? = null

    companion object {
        const val TAG = "垃圾搜索页面"

        fun startActivity(context: Context?, name: String? = null) {
            val intent = Intent(context, SearchActivity::class.java)
            if (name != null) {
                intent.putExtra("name", name)
            }
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        garbageName = intent.getStringExtra("name")
        initView()
        if (!garbageName.isNullOrEmpty()) {
            etSearchWord.setTextViewText(garbageName)
            fetchSearchResult(garbageName!!)
        } else {
            fetchHotWords()
        }
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tv_title)
        ivBack = findViewById(R.id.iv_back)
        vDivider = findViewById(R.id.v_divider)
        etSearchWord = findViewById(R.id.et_search_garbage)
        ivClear = findViewById(R.id.iv_clear)
        llHotSearch = findViewById(R.id.ll_hot_search)
        rvHotSearch = findViewById(R.id.rv_hot_search)
        rvSearchResult = findViewById(R.id.rv_search_detail)
        etSearchWord?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (!text.isNullOrEmpty()) {
                    ivClear.setViewVisibility(View.VISIBLE)
                } else {
                    ivClear.setViewVisibility(View.INVISIBLE)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etSearchWord?.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == 0) {
                rvHotSearch.setViewVisibility(View.GONE)
                fetchSearchResult(textView.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        ivClear?.setOnClickListener {
            etSearchWord?.setText("")
            rvSearchResult.setViewVisibility(View.GONE)
            llHotSearch.setViewVisibility(View.VISIBLE)
        }
        tvTitle?.text = "搜索"
        ivBack.setViewVisibility(View.VISIBLE)
        ivBack?.setOnClickListener {
            finish()
        }

    }

    @SuppressLint("CheckResult")
    fun fetchHotWords() {
        GarbageSearchNetwork.fetchHotWords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.code == 200) {
                    parseHotWords(it.newslist)
                } else {
                    Toast.makeText(this, "网络请求错误", Toast.LENGTH_SHORT).show()
                    when (it.code) {
                        110 -> Log.d(TAG, throw RuntimeException("接口暂时维护中"))
                        150 -> Log.d(TAG, throw RuntimeException("接口请求次数不足"))
                        230 -> Log.d(TAG, throw RuntimeException("参数key错误"))
                    }
                }
            },{
                Toast.makeText(this, "网络请求失败", Toast.LENGTH_SHORT).show()
                Log.d(TAG, throw RuntimeException("网络请求失败"))
            })
    }

    private fun parseHotWords(data: List<HotWordsListBean>?) {
        if (data == null) {
            return
        }
        if (hotWordsAdapter == null) {
            hotWordsAdapter = HotWordsAdapter(data)
            hotWordsAdapter?.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(name: String) {
                    etSearchWord.setTextViewText(name)
                    rvHotSearch.setViewVisibility(View.GONE)
                    rvSearchResult.setViewVisibility(View.VISIBLE)
                    fetchSearchResult(name)
                }
            })
            rvHotSearch?.let {
                it.adapter = hotWordsAdapter
                it.layoutManager = LinearLayoutManager(this)
            }
        } else {
            hotWordsAdapter?.refreshDataList(data)
        }
    }

    @SuppressLint("CheckResult")
    fun fetchSearchResult(word: String) {
        GarbageSearchNetwork.fetchGarbageDetail(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.code == 200) {
                    parseSearchResult(it.newslist)
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

    private fun parseSearchResult(data: List<DetailListBean>?) {
        if (data == null) {
            return
        }
        llHotSearch.setViewVisibility(View.GONE)
        rvSearchResult.setViewVisibility(View.VISIBLE)
        if (searchResultAdapter == null) {
            searchResultAdapter = SearchResultAdapter(data)
            rvSearchResult?.let {
                it.adapter = searchResultAdapter
                it.layoutManager = LinearLayoutManager(this)
            }
        } else {
            searchResultAdapter?.refreshDataList(data)
        }
    }
}