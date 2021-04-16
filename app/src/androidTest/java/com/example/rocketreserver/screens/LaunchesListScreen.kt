package com.example.rocketreserver.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.progress.KProgressBar
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.rocketreserver.R
import org.hamcrest.Matcher

object LaunchesListScreen : Screen<LaunchesListScreen>() {
    val progressBar = KProgressBar { withId(R.id.launchesProgressBar) }
    val errorView = KView { withId(R.id.launchesErrorView) }
    val recyclerView = KRecyclerView(
        builder = {
            withId(R.id.launchesRecyclerView)
        },
        itemTypeBuilder = {
            itemType(::LaunchItem)
        }
    )

    class LaunchItem(parent: Matcher<View>) : KRecyclerItem<LaunchItem>(parent) {
        val site: KTextView = KTextView(parent) { withId(R.id.site) }
    }
}