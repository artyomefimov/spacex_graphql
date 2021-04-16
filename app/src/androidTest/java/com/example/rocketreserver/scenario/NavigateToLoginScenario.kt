package com.example.rocketreserver.scenario

import com.example.rocketreserver.screens.LaunchDetailsScreen
import com.example.rocketreserver.screens.LoginScreen
import com.kaspersky.kaspresso.testcases.api.scenario.BaseScenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

class NavigateToLoginScenario<ScenarioData> : BaseScenario<ScenarioData>() {

    override val steps: TestContext<ScenarioData>.() -> Unit = {
        step("При нажатии на кнопку бронирования при отсутствии токена переходим на экран логина") {
            LaunchDetailsScreen {
                bookButton.click()
            }
            LoginScreen {
                contentGroup.isVisible()
            }
        }
    }
}