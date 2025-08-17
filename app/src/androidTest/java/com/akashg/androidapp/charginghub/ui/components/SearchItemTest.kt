package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import com.akashg.androidapp.charginghub.ComponentTest
import org.junit.Test

class SearchItemTest : ComponentTest() {

    @Test
    fun searchItem_displaysLocationNameAndAddress() {
        setContent {
            SearchItem(locationName = "Location Name", locationAddress = "Location Address")
        }
        with(composeTestRule) {
            onNodeWithText("Location Name").assertIsDisplayed()
            onNodeWithText("Location Address").assertIsDisplayed()
        }
    }
}
