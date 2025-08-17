package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import com.akashg.androidapp.charginghub.ComponentTest
import com.akashg.androidapp.charginghub.R
import org.junit.Test

class LocationInfoTest : ComponentTest() {

    @Test
    fun locationInfo_displaysDistance() {
        val distance = "10"
        setContent {
            LocationInfo(
                distance = distance,
            )
        }
        with(composeTestRule) {
            onNodeWithText(distance).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.distance_unit)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.vehicle_type)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.ccs_type)).assertIsDisplayed()
        }
    }
}
