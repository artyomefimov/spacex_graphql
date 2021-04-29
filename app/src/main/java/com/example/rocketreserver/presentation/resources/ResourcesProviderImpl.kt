package com.example.rocketreserver.presentation.resources

import android.content.Context

class ResourcesProviderImpl(
    private val context: Context
): ResourcesProvider {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}
