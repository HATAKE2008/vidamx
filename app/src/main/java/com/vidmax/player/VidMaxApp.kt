package com.vidmax.player

import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Process
import com.vidmax.player.ui.crash.CrashActivity
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class VidMaxApp : Application() {
override fun onCreate() {
super.onCreate()

Logger.initialize(this);


// অ্যাপের গ্লোবাল ক্র্যাশ লিসেনার সেট করা হচ্ছে
Thread.setDefaultUncaughtExceptionHandler { thread: Thread, exception: Throwable ->

// Stacktrace বের করা
val stringWriter = StringWriter()
exception.printStackTrace(PrintWriter(stringWriter))
val stackTrace = stringWriter.toString()

// ডিভাইসের মডেল এবং অ্যান্ড্রয়েড ভার্সন বের করা
val deviceInfo = """
📱 Device Info:
Brand: ${Build.BRAND}
Model: ${Build.MODEL}
Device: ${Build.DEVICE}
Android Version: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})

⚠️ --- Crash Log ---

""".trimIndent()

val fullErrorLog = deviceInfo + stackTrace

// ক্র্যাশ স্ক্রিনে ডাটা পাঠানো
val intent = Intent(this, CrashActivity::class.java).apply {
putExtra("EXTRA_ERROR_DETAILS", fullErrorLog)
addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
}
startActivity(intent)

// ব্যাকগ্রাউন্ডের মরা প্রসেস কিল করে দেওয়া
Process.killProcess(Process.myPid())
exitProcess(1)
}
}
}