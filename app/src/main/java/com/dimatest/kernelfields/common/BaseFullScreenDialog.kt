package com.dimatest.kernelfields.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dimatest.kernelfields.R

abstract class BaseFullScreenDialog<DB : ViewDataBinding> : DialogFragment() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    lateinit var binding: DB

    override fun getTheme() = R.style.FullscreenDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val matchPatent = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(matchPatent, matchPatent)
    }

    open fun show(fm: FragmentManager?) {
        fm?.let { show(it, this.javaClass.simpleName) }
    }
}