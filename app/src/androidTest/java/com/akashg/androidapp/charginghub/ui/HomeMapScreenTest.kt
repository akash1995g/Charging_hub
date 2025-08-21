package com.akashg.androidapp.charginghub.ui

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.akashg.androidapp.charginghub.ComposeUITest
import com.akashg.androidapp.charginghub.HiltTestActivity
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.repo.GetCurrentLocationUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

@HiltAndroidTest
class HomeMapScreenTest : ComposeUITest() {
    @BindValue
    val getCurrentLocationUseCase: GetCurrentLocationUseCase = mockk()

    @Test
    fun test_Map_Screen() {
        initialize()
        with(composeTestRule) {
            onNodeWithContentDescription(getStringFromRes(R.string.get_current_location)).assertIsDisplayed()
            onNodeWithContentDescription(getStringFromRes(R.string.menu_option)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder)).assertIsDisplayed()
        }
    }

    @Test
    fun test_MenuList_Content_Scroll() = runTest {
        initialize()

        with(composeTestRule) {
            onNodeWithTag(TEST_TAG_MAP_CARD_INFO_SECTION).assertIsDisplayed()
            getMyLocation()
            onNodeWithContentDescription(getStringFromRes(R.string.menu_option)).assertIsDisplayed()
                .performClick()
            with(onNodeWithTag(TEST_TAG_MENU_SECTION)) {
                onNodeWithText("Banashankari, Bengaluru", substring = true)
                    .assertIsDisplayed()
                performScrollToNode(
                    hasText(
                        "KR Puram, Bengaluru",
                        substring = true,
                    ),
                ).assertIsDisplayed()

                performScrollToNode(
                    hasText(
                        "Guwahati, India",
                        substring = true,
                    ),
                ).assertIsDisplayed()
            }
        }
    }

    @Test
    fun test_SearchList_Content_ScrollAndSearch() = runTest {
        initialize()
        with(composeTestRule) {
            getMyLocation()
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder)).performClick()

            onNodeWithTag(TEST_TAG_SEARCH_SECTION).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder)).performTextInput("m")
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder)).assertDoesNotExist()

            onNodeWithTag(TEST_TAG_SEARCH_SECTION)
                .performScrollToNode(
                    hasText(
                        "Ahmedabad, India",
                        substring = true,
                    ),
                ).assertIsDisplayed()
            onNodeWithContentDescription(getStringFromRes(R.string.clear)).assertIsDisplayed()
                .performClick()
            onNodeWithContentDescription(getStringFromRes(R.string.back)).assertIsDisplayed()
                .performClick()
            onNodeWithTag(TEST_TAG_MAP_CARD_INFO_SECTION).assertIsDisplayed()
        }
    }

    @Test
    fun test_Map() {
        initialize()

        with(composeTestRule) {
            getMyLocation()
            onNodeWithTag(TEST_TAG_MAP_CARD_INFO_SECTION).assertIsDisplayed()
            onNodeWithTag("Map").assertIsDisplayed()
        }
    }

    @Test
    fun test_SearchBar_Item_Click() = runTest {
        initialize()
        with(composeTestRule) {
            getMyLocation()
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder)).assertIsDisplayed()
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder)).performTextInput("Ahmedabad, India")
            onNodeWithTag(TEST_TAG_SEARCH_SECTION).onChildren()
                .assertAny(hasText("Ahmedabad, India"))[0].assertIsDisplayed().performClick()
        }
    }

    @Test
    fun test_Menu_Item_Click() = runTest {
        initialize()
        with(composeTestRule) {
            getMyLocation()
            onNodeWithContentDescription(getStringFromRes(R.string.menu_option)).performClick()
            onNodeWithTag(TEST_TAG_MENU_SECTION).performScrollToNode(
                hasText(
                    "Ahmedabad, India",
                    substring = true,
                ),
            ).assertIsDisplayed().performClick()
        }
    }

    private fun initialize() {
        grantPermission(
            listOf(
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION",
            ),
        )
        coEvery { getCurrentLocationUseCase() } returns LatLng(12.952235, 77.543683)
        setContent {
            HomeMapScreen()
        }
    }

    private fun AndroidComposeTestRule<ActivityScenarioRule<HiltTestActivity>, HiltTestActivity>.getMyLocation() {
        onNodeWithContentDescription(getStringFromRes(R.string.get_current_location)).assertIsDisplayed()
        onNodeWithContentDescription(getStringFromRes(R.string.get_current_location)).performClick()
    }
}
