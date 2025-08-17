package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.akashg.androidapp.charginghub.ComponentTest
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.ui.theme.customColors
import org.junit.Test

class InfoBadgeTest : ComponentTest() {

    @Test
    fun infoBadge_displaysTextWithIcon() {
        val testText = "Badge"
        val contentDescription = "Content Description"

        setContent {
            InfoBadge(
                icon = R.drawable.icon_star,
                text = testText,
                backgroundColor = MaterialTheme.customColors.yellowColor,
                iconDescription = contentDescription,
            )
        }

        with(composeTestRule) {
            onNodeWithText(testText).assertIsDisplayed()
            onNodeWithContentDescription(contentDescription).assertIsDisplayed()
        }
    }

    @Test
    fun infoBadge_displaysTextOnly() {
        val testText = "No Icon"
        val contentDescription = "Content Description"

        setContent {
            InfoBadge(
                text = testText,
                backgroundColor = MaterialTheme.customColors.yellowColor,
                iconDescription = contentDescription,
            )
        }

        with(composeTestRule) {
            onNodeWithText(testText).assertIsDisplayed()
            onNodeWithContentDescription(contentDescription).assertDoesNotExist()
        }
    }
}
