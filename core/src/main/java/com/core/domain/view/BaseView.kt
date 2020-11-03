package com.core.domain.view

interface BaseView {
    fun init()
    fun showContent()
    fun hideContent()
    fun showText(string: String)
}