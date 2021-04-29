package com.example.rocketreserver.presentation.resources

import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes resId: Int): String
}
