package com.akashg.androidapp.charginghub

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.akashg.androidapp.charginghub.ui.theme.ChargingHubTheme
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule

abstract class ComposeUITest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val automation = InstrumentationRegistry.getInstrumentation().uiAutomation

    @Before
    fun injectHilt() {
        hiltRule.inject()
    }

    fun setContent(content: @Composable () -> Unit) {
        composeTestRule.setContent {
            ChargingHubTheme {
                content()
            }
        }
    }

    fun grantPermission(permissionList: List<String> = listOf()) {
        permissionList.forEach {
            automation.executeShellCommand("pm grant ${context.packageName} $it")
                .close()
        }
    }

    fun getStringFromRes(@StringRes int: Int): String {
        return context.getString(int)
    }
}
