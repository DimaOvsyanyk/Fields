package com.dimatest.kernelfields.dialogs

import com.dimatest.kernelfields.R
import com.dimatest.kernelfields.common.BaseFullScreenDialog
import com.dimatest.kernelfields.databinding.DialogFullscreenLoaderBinding

class FullScreenLoaderDialog : BaseFullScreenDialog<DialogFullscreenLoaderBinding>() {

    override fun getLayoutRes() = R.layout.dialog_fullscreen_loader
}