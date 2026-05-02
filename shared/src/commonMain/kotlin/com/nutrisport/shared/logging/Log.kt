package com.nutrisport.shared.logging

import logkat.LogKat

object Log {

    fun d(tag: String, message: String) {
        LogKat.d(tag, message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        val formatedMessage = if (throwable != null) {
            "$message\n${throwable.stackTraceToString()}"
        } else {
            message
        }
        LogKat.e(tag, formatedMessage)
    }

    fun i(tag: String, message: String) {
        LogKat.i(tag, message)
    }


}