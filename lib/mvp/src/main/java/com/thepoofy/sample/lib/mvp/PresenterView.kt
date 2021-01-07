package com.thepoofy.sample.lib.mvp

import androidx.viewbinding.ViewBinding

interface PresenterView<VB : ViewBinding> {

    fun onAttach(binding: VB)
}