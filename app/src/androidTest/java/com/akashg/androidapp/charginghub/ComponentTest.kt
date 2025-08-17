package com.akashg.androidapp.charginghub

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.akashg.androidapp.charginghub.ui.theme.ChargingHubTheme
import org.junit.Rule

abstract class ComponentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    fun setContent(content: @Composable () -> Unit) {
        composeTestRule.setContent {
            ChargingHubTheme {
                content()
            }
        }
    }

    fun getStringFromRes(@StringRes resId: Int) = context.getString(resId)
}
