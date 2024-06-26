package com.e_book.MaterialList
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.e_book.R
import com.e_book.ThemeActivity

class PDFViewActivity :ThemeActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfview)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)

        val fileUrl = intent.getStringExtra("FILE_URL") ?: return

        // Configure WebView settings
        webView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                }
            }

            settings.apply {
                javaScriptEnabled = true
                builtInZoomControls = true
                displayZoomControls = false
                layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                useWideViewPort = true
                loadWithOverviewMode = true
                allowFileAccess = true
                setSupportMultipleWindows(true)
                setSupportZoom(true)
                scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            }
            // Check if the file is a PDF
            if (fileUrl.endsWith(".pdf")) {
                // Use Google Docs Viewer to load the PDF
                val googleDocsUrl = "https://docs.google.com/viewer?url=$fileUrl"
                loadUrl(googleDocsUrl)
            } else {
                // Load the URL directly in WebView for other files (like images)
                loadUrl(fileUrl)
            }
        }
    }
}
