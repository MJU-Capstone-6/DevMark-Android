package com.devmark.devmark.data.utils

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

object LoggerUtils {

    init {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    private const val TAG = "LOGGER"

    fun debug(message: String) {
        Logger.t(TAG).d(message)
    }

    fun info(message: String) {
        Logger.t(TAG).i(message)
    }

    fun warning(message: String) {
        Logger.t(TAG).w(message)
    }

    fun error(message: String) {
        Logger.t(TAG).e(message)
    }
}