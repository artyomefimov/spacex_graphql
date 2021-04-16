package com.example.rocketreserver.screens

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView

object MainScreen : Screen<MainScreen>() {
    val snackbar = KTextView {
        withId(com.google.android.material.R.id.snackbar_text)
    }
}