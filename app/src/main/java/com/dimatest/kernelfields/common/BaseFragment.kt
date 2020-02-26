package com.dimatest.kernelfields.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.dimatest.kernelfields.MainActivity
import com.dimatest.kernelfields.utils.observeNotNull

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract val viewModel: VM
    lateinit var dataBinding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return dataBinding.root
    }

    //ViwModel dataBinding variable in all fragments should be named "viewModel"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.setVariable(BR.viewModel, viewModel)
        dataBinding.lifecycleOwner = this
        dataBinding.executePendingBindings()
        observeBaseModel()
    }

    private fun observeBaseModel() {
        viewModel.loading.observeNotNull(viewLifecycleOwner) {
            getMainActivity().showLoader(it)
        }
    }

    fun navigate(destId: Int) {
        getNavController().navigate(destId)
    }

    fun navigate(directions: NavDirections) {
        getNavController().navigate(directions)
    }

    fun getNavController() = getMainActivity().navController

    fun getMainActivity() = (requireActivity() as MainActivity)

}