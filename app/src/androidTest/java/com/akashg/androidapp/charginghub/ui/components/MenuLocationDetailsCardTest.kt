package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akashg.androidapp.charginghub.ComponentTest
import com.akashg.androidapp.charginghub.R
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MenuLocationDetailsCardTest : ComponentTest() {
    @Test
    fun menuLocationDetailsCard_displaysDataCorrectly() {
        val testPlace = "Central Park Charging"
        val testDistance = "5 km"
        val mockCallAction: () -> Unit = mockk(relaxed = true)
        val mockNavigateAction: () -> Unit = mockk(relaxed = true)

        setContent {
            MenuLocationDetailsCard(
                place = testPlace,
                distance = testDistance,
                callAction = mockCallAction,
                navigateAction = mockNavigateAction,
            )
        }

        with(composeTestRule) {
            onNodeWithText("$testPlace - $testDistance").assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.place_name)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.fast_charge)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.star))
                .assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.time))
                .assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.contact)).assertIsDisplayed().performClick()
            onNodeWithText(getStringFromRes(R.string.navigate)).assertIsDisplayed().performClick()
            onNodeWithText(getStringFromRes(R.string.available)).assertIsDisplayed()
        }
        verify { mockCallAction.invoke() }
        verify { mockNavigateAction.invoke() }
    }
}
