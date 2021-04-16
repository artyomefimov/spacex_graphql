package com.example.rocketreserver.screens

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.rocketreserver.R

object LaunchDetailsScreen : Screen<LaunchDetailsScreen>() {
    val contentGroup = KView { withId(R.id.detailsContentGroup) }
    val bookButton = KView { withId(R.id.bookButton) }
    val buttonTextView = KTextView { withId(R.id.buttonTextView) }
}
