package com.example.knowledgedisplay

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.base.BaseActivity
import com.example.utils.setTextViewText
import com.example.utils.setViewVisibility
import com.gyf.immersionbar.ImmersionBar

class WebviewActivity : BaseActivity() {

    private var webview: WebView? = null
    private var ivBack: ImageView? = null
    private var tvTitle: TextView? = null
    private var container: ConstraintLayout? = null
    private var clTitle: ConstraintLayout? = null
    private var progressbar: ProgressBar? = null
    private var webUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webUrl = intent.getStringExtra("url") ?: ""
        initView()
        webview?.loadUrl(webUrl)
    }

    private fun initView() {
        ivBack = findViewById(R.id.iv_back)
        tvTitle = findViewById(R.id.tv_title)
        webview = findViewById(R.id.webview)
        container = findViewById(R.id.cl_container)
        clTitle = findViewById(R.id.cl_title)
        progressbar = findViewById(R.id.progressbar)
        val divider: View? = clTitle?.findViewById(R.id.v_divider)
        divider.setViewVisibility(View.VISIBLE)
        initWebView()
        ivBack.setViewVisibility(View.VISIBLE)
        ivBack?.setOnClickListener {
            finish()
        }
        tvTitle.setTextViewText("垃圾分类新闻详情")
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .titleBar(clTitle)
            .fitsSystemWindows(true)
            .init()
    }


    private fun initWebView() {
        webview?.settings?.apply {
            javaScriptEnabled = true
            //设置自适应屏幕，两者合用
            useWideViewPort = true          //调整到适合webview的大小
            loadWithOverviewMode = true     // 缩放至屏幕的大小
            javaScriptCanOpenWindowsAutomatically = true    //支持通过JS打开新窗口
            loadsImagesAutomatically = true                 //支持自动加载图片
            setSupportZoom(true)

        }
        webview?.apply {
            //移除对指定的javascript接口的调用,防止webview远程代码执行漏洞
            removeJavascriptInterface("searchBoxJavaBridge_")
            removeJavascriptInterface("accessibility")
            removeJavascriptInterface("accessibilityTraversal")

            webChromeClient = MyWebChromeClient()
            webViewClient = MyWebViewClient()
        }
    }

    override fun onBackPressed() {
        webview?.let {
            if (it.canGoBack()) {
                it.goBack()
            } else {
                finish()
            }
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressbar?.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressbar?.visibility = View.GONE
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            progressbar?.visibility = View.GONE
        }
    }

    inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressbar?.progress = newProgress
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if (title != null) {
                tvTitle.setTextViewText(title)
            }
        }
    }

    override fun onDestroy() {
        //下面主要是为了防止webview内存泄漏
        webview?.let {
            it.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            it.clearHistory()
            container?.removeView(it)
            it.destroy()
        }
        super.onDestroy()
    }
}