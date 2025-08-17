package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akashg.androidapp.charginghub.ComponentTest
import com.akashg.androidapp.charginghub.R
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MapLocationDetailsCardTest : ComponentTest() {

    @Test
    fun mapLocationDetailsCard_displaysCorrectly() {
        val testTitle = "Test Location Title"
        val testDistance = "10 km"
        val mockCallAction: () -> Unit = mockk(relaxed = true)
        val mockNavigateAction: () -> Unit = mockk(relaxed = true)

        setContent {
            MapLocationDetailsCard(
                title = testTitle,
                distance = testDistance,
                callAction = mockCallAction,
                navigateAction = mockNavigateAction,
            )
        }

        with(composeTestRule) {
            onNodeWithText(testTitle).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.fast_charge))
                .assertIsDisplayed()
            onNodeWithText(testDistance, substring = true).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.available)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.star)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.time)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.contact).uppercase())
                .assertIsDisplayed().performClick()
            onNodeWithText(getStringFromRes(R.string.navigate).uppercase())
                .assertIsDisplayed().performClick()
        }
        verify { mockCallAction.invoke() }
        verify { mockNavigateAction.invoke() }
    }
}
