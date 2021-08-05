package com.example.thesearcher.view.BrowserView

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.thesearcher.R

val INTENT_EXTRA_PAGE_URL = "intentExtraPageUrl"

class BrowserActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true

        webView.loadUrl(
            intent.getStringExtra(INTENT_EXTRA_PAGE_URL) ?: ""
        )

    }
}