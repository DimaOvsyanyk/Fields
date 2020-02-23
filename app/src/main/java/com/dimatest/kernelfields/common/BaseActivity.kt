package com.dimatest.kernelfields.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dimatest.kernelfields.dialogs.FullScreenLoaderDialog

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    lateinit var binding: DB

    private val loaderDialog: FullScreenLoaderDialog by lazy {
        FullScreenLoaderDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
    }

    fun showLoader(show: Boolean) {
        if (show)
            loaderDialog.show(supportFragmentManager)
        else
            loaderDialog.dismiss()
    }
}