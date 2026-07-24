package com.vidmax.player

import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Process
import android.util.Log
import com.vidmax.player.ui.crash.CrashActivity
import dagger.hilt.android.HiltAndroidApp
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.downloader.Request
import org.schabi.newpipe.extractor.downloader.Response
import java.io.InputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.system.exitProcess

@HiltAndroidApp // 🔥 Hilt-এর জন্য এটি অত্যাবশ্যক
class VidMaxApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        Log.d("VidMaxApp", "VidMax Player initialized successfully!")

        // 🔥 Music Streaming-এর জন্য NewPipe Extractor ইনিশিয়ালাইজ
        NewPipe.init(getDownloader())

        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
            val stringWriter = StringWriter()
            exception.printStackTrace(PrintWriter(stringWriter))
            
            val deviceInfo = """
            📱 Device Info:
            Brand: ${Build.BRAND}
            Model: ${Build.MODEL}
            Android Version: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})

            ⚠️ --- Crash Log ---

            """.trimIndent()

            val intent = Intent(this, CrashActivity::class.java)
            intent.putExtra("EXTRA_ERROR_DETAILS", deviceInfo + stringWriter.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

            Process.killProcess(Process.myPid())
            exitProcess(1)
        }
    }

    // 🔥 NewPipe-এর নেটওয়ার্ক রিকোয়েস্ট হ্যান্ডেল করার ফাংশন
    private fun getDownloader(): Downloader {
        return object : Downloader() {
            override fun execute(request: Request): Response {
                val url = URL(request.url())
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = request.httpMethod()
                
                request.headers().forEach { (key, value) ->
                    connection.setRequestProperty(key, value.firstOrNull())
                }

                val responseCode = connection.responseCode
                val responseMessage = connection.responseMessage
                
                val inputStream: InputStream = if (responseCode in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream ?: connection.inputStream
                }

                val responseBody = inputStream.bufferedReader().use { it.readText() }
                
                return Response(
                    responseCode,
                    responseMessage,
                    connection.headerFields.mapValues { listOf(it.value.joinToString(",")) },
                    responseBody,
                    request.url()
                )
            }
        }
    }
}
