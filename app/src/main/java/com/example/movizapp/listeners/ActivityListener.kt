package com.example.movizapp.listeners

import androidx.fragment.app.Fragment

interface ActivityListener {
    fun navigateToFragment(fragment: Fragment)
    fun setTitle(title:String)
}