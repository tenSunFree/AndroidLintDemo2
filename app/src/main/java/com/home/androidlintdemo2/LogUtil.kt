package com.home.androidlintdemo2

import android.annotation.SuppressLint
import android.util.Log

object LogUtil {

    @SuppressLint("NoLog")
    fun d(tag: String, msg: String) {
        val targetStackTraceElement =
            targetStackTraceElement
        val fileName = targetStackTraceElement!!.fileName
        val lineNumber = targetStackTraceElement.lineNumber
        Log.d(tag, "$msg  ($fileName:$lineNumber)")
    }

    private val targetStackTraceElement: StackTraceElement?
        get() {
            var targetStackTrace: StackTraceElement? = null
            var shouldTrace = false
            val stackTrace =
                Thread.currentThread().stackTrace
            for (stackTraceElement in stackTrace) {
                val isLogMethod =
                    stackTraceElement.className == LogUtil::class.java.name
                if (shouldTrace && !isLogMethod) {
                    targetStackTrace = stackTraceElement
                    break
                }
                shouldTrace = isLogMethod
            }
            return targetStackTrace
        }
}