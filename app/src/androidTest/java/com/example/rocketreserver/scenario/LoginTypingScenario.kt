package com.example.rocketreserver.scenario

import androidx.test.espresso.Espresso
import com.example.rocketreserver.screens.LaunchDetailsScreen
import com.example.rocketreserver.screens.LoginScreen
import com.kaspersky.kaspresso.testcases.api.scenario.BaseScenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

class LoginTypingScenario<ScenarioData> : BaseScenario<ScenarioData>() {

    companion object {
        const val INCORRECT_EMAIL = "ABC@D"
        const val CORRECT_EMAIL = "me@simbirsoft.com"
    }

    override val steps: TestContext<ScenarioData>.() -> Unit = {
        step("При вводе некорректного емейла показывается ошибка") {
            LoginScreen {
                emailField {
                    typeText(INCORRECT_EMAIL)
                    Espresso.closeSoftKeyboard()
                    hasText(INCORRECT_EMAIL)
                }
                submitButton.click()
                emailLayout {
                    isErrorEnabled()
                    hasError("Invalid email")
                }
            }
        }
        step("Стереть текст") {
            LoginScreen.emailField.clearText()
        }
        step("При вводе корректного емейла происходит переход на экран деталей") {
            LoginScreen {
                emailField {
                    typeText(CORRECT_EMAIL)
                    Espresso.closeSoftKeyboard()
                    hasText(CORRECT_EMAIL)
                }
                submitButton.click()
            }
            LaunchDetailsScreen {
                contentGroup.isVisible()
            }
        }
    }
}
