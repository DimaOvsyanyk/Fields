package com.dimatest.kernelfields.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.dimatest.kernelfields.R

class ErrorHandler(private val context: Context) {

    fun processError(throwable: Throwable) {
        showErrorDialog(throwable)
        logError(throwable)
    }

    fun showErrorDialog(throwable: Throwable) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.title_error))
            .setMessage(throwable.message)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    fun logError(throwable: Throwable) {
        Log.e(TAG, "error happened: ", throwable)
    }

    companion object {
        const val TAG = "ErrorHandler"
    }
}