package com.example.rocketreserver.scenario

import com.agoda.kakao.screen.Screen
import com.example.rocketreserver.screens.LaunchDetailsScreen
import com.example.rocketreserver.screens.LaunchesListScreen
import com.kaspersky.kaspresso.testcases.api.scenario.BaseScenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

class NavigateToDetailsScenario<ScenarioData> : BaseScenario<ScenarioData>() {

    override val steps: TestContext<ScenarioData>.() -> Unit = {
        step("При нажатии на первый элемент переходим на экран деталей") {
            LaunchesListScreen {
                Screen.idle(duration = 2000L)
                recyclerView {
                    childAt<LaunchesListScreen.LaunchItem>(10) { perform { click() } }
                }
            }
            LaunchDetailsScreen {
                Screen.idle(duration = 2000L)
                contentGroup.isVisible()
            }
        }
    }
}
