package com.example.thesearcher.view.BrowserView

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import com.example.thesearcher.R

class BrowserActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)



        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        val a = intent.getStringExtra("pageUrl")!!
        Log.d("dbg", a.toString())
        webView.loadUrl(a!!) //TODO :: Handle me

    }
}