package com.vidmax.player

import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Process
import android.util.Log
import com.vidmax.player.ui.crash.CrashActivity
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class VidMaxApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        Log.d("VidMaxApp", "VidMax Player initialized successfully!")

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
}
