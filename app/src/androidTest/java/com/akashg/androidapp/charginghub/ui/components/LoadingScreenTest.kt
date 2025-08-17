package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.akashg.androidapp.charginghub.ComponentTest
import org.junit.Test

class LoadingScreenTest : ComponentTest() {

    @Test
    fun testLoadingScreen() {
        setContent {
            LoadingScreen()
        }
        with(composeTestRule) {
            onNodeWithText("Loading...").assertIsDisplayed()
            onNodeWithTag(LOADING_SCREEN_TEST_TAG).assertIsDisplayed()
        }
    }
}
