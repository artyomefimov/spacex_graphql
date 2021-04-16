package com.example.rocketreserver.scenario

import com.example.rocketreserver.R
import com.example.rocketreserver.screens.LaunchDetailsScreen
import com.example.rocketreserver.screens.MainScreen
import com.kaspersky.kaspresso.testcases.api.scenario.BaseScenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

class BookUnbookScenario<ScenarioData> : BaseScenario<ScenarioData>() {

    override val steps: TestContext<ScenarioData>.() -> Unit = {
        step("Нажатие на кнопку бронирования меняет текст кнопки и показывает снекбар") {
            LaunchDetailsScreen {
                contentGroup.isVisible()
                buttonTextView.hasText(R.string.book_now)
                bookButton.click()
            }
            MainScreen {
                snackbar.isVisible()
            }
            LaunchDetailsScreen {
                buttonTextView.hasText(R.string.cancel)
                bookButton.click()
            }
            MainScreen {
                snackbar.isVisible()
            }
        }
    }
}
