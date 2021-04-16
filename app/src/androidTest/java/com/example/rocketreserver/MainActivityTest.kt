package com.example.rocketreserver

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.rocketreserver.presentation.view.MainActivity
import com.example.rocketreserver.scenario.BookUnbookScenario
import com.example.rocketreserver.scenario.LoginTypingScenario
import com.example.rocketreserver.scenario.NavigateToDetailsScenario
import com.example.rocketreserver.scenario.NavigateToLoginScenario
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : TestCase() {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun mainScenarioTest() {
        before {
            activityRule.launchActivity(null)
        }.after {

        }.run {
            scenario(NavigateToDetailsScenario())
            scenario(NavigateToLoginScenario())
            scenario(LoginTypingScenario())
            scenario(BookUnbookScenario())
        }
    }
}
