package com.sigarda.donora.ui.fragment.base

import android.view.View
import androidx.fragment.app.Fragment
import com.sigarda.donora.ui.activity.MainActivity

abstract class BaseFragment : Fragment() {


    open var toolbarVisibility = false
    open var bottomNavigationViewVisibility = View.VISIBLE

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            val mainActivity =  activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
            mainActivity.setToolbarVisibility(toolbarVisibility)
        }
    }



}