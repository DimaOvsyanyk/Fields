package com.dimatest.kernelfields

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dimatest.kernelfields.common.BaseActivity
import com.dimatest.kernelfields.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes() = R.layout.activity_main
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.main_nav_host)
    }
}
