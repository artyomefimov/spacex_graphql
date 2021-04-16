package com.example.rocketreserver.screens

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.edit.KTextInputLayout
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.example.rocketreserver.R

object LoginScreen : Screen<LoginScreen>() {
    val contentGroup = KView { withId(R.id.contentGroup) }
    val emailLayout = KTextInputLayout { withId(R.id.email_layout) }
    val emailField = KEditText { withId(R.id.email) }
    val submitButton = KButton { withId(R.id.submitButton) }
}