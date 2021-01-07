package com.thepoofy.sample.lib.mvp

import android.view.LayoutInflater
import android.view.ViewGroup

interface PresenterView {

    fun onAttach(layoutInflater: LayoutInflater, viewGroup: ViewGroup)
}