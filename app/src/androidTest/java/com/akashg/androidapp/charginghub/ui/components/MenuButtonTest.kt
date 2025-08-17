package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.akashg.androidapp.charginghub.ComponentTest
import com.akashg.androidapp.charginghub.R
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MenuButtonTest : ComponentTest() {

    @Test
    fun menuButton_displaysCorrectly_and_handlesClick() {
        val testContentDescription = "Test Menu Button"
        val mockOnClick: () -> Unit = mockk(relaxed = true)

        setContent {
            MenuButton(
                iconId = R.drawable.icon_gps_fixed,
                contentDescription = testContentDescription,
                onClick = mockOnClick,
            )
        }
        with(composeTestRule.onNodeWithContentDescription(testContentDescription)) {
            assertIsDisplayed()
            performClick()
            verify { mockOnClick.invoke() }
        }
    }
}
