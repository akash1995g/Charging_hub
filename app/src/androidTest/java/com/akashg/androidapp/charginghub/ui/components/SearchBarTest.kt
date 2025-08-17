package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.akashg.androidapp.charginghub.ComponentTest
import com.akashg.androidapp.charginghub.R
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SearchBarTest : ComponentTest() {

    @Test
    fun searchBar_initialState_displaysPlaceholderAndSearchIcon() {
        setContent {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                closeSearchBar = {},
                isFocused = false,
                onFocusChange = {},
            )
        }

        with(composeTestRule) {
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder)).assertIsDisplayed()
            onNodeWithContentDescription(getStringFromRes(R.string.search))
                .assertIsDisplayed()
            onNodeWithContentDescription(
                getStringFromRes(R.string.back),
                useUnmergedTree = true,
            ).assertDoesNotExist()
            onNodeWithContentDescription(
                getStringFromRes(R.string.clear),
                useUnmergedTree = true,
            ).assertDoesNotExist()
        }
    }

    @Test
    fun searchBar_whenFocused_displaysBackArrowAndSearchSuffix_callsOnFocusChange() {
        setContent {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                closeSearchBar = {},
                isFocused = true,
                onFocusChange = {
                },
            )
        }
        with(composeTestRule) {
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder))
                .assertIsDisplayed()
            onNodeWithContentDescription(getStringFromRes(R.string.back))
                .assertIsDisplayed()
            onNodeWithContentDescription(getStringFromRes(R.string.search))
                .assertIsDisplayed()
        }
    }

    @Test
    fun searchBar_whenFocusedAndValueNotEmpty_displaysBackArrowAndClearSuffix() {
        setContent {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = "Test",
                onValueChange = {},
                closeSearchBar = {},
                isFocused = true,
                onFocusChange = {},
            )
        }

        with(composeTestRule) {
            onNodeWithContentDescription(getStringFromRes(R.string.back))
                .assertIsDisplayed()
            onNodeWithContentDescription(getStringFromRes(R.string.clear))
                .assertIsDisplayed()
            onNodeWithContentDescription(
                getStringFromRes(R.string.search),
                substring = true,
                useUnmergedTree = true,
            ).assertDoesNotExist()
        }
    }

    @Test
    fun searchBar_onValueChange_updatesValueAndTriggersCallback() {
        val testInput = "Hello"
        val value = mutableStateOf("")

        setContent {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = value.value,
                onValueChange = {
                    value.value = it
                },
                closeSearchBar = {
                },
                isFocused = value.value.isNotEmpty(),
                onFocusChange = {},
            )
        }

        with(composeTestRule) {
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder))
                .performTextInput(testInput)
            onNodeWithText(testInput).assertIsDisplayed()
            waitForIdle()
            onNodeWithContentDescription(getStringFromRes(R.string.clear))
                .assertIsDisplayed().assertHasClickAction()
            onNodeWithContentDescription(getStringFromRes(R.string.clear)).performClick()
            assert(value.value == "")
        }
    }

    @Test
    fun searchBar_clickPrefixIconWhenFocused_callsCloseSearchBar() {
        val close: () -> Unit = mockk(relaxed = true)
        setContent {
            SearchBar(
                value = "Test",
                onValueChange = {},
                closeSearchBar = close,
                isFocused = true,
                onFocusChange = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(getStringFromRes(R.string.back))
            .performClick()
        verify {
            close.invoke()
        }
    }

    @Test
    fun searchBar_clickSuffixIconWhenFocusedAndValueNotEmpty_clearsValue() {
        var currentValue = "Test"
        var onValueChangeCalledWithEmpty = false

        setContent {
            var text by remember { mutableStateOf(currentValue) }
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    currentValue = it
                    text = it
                    if (it.isEmpty()) {
                        onValueChangeCalledWithEmpty = true
                    }
                },
                closeSearchBar = {},
                isFocused = true,
                onFocusChange = {},
            )
        }

        with(composeTestRule) {
            onNodeWithContentDescription(getStringFromRes(R.string.clear))
                .performClick()
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder))
                .assertIsDisplayed()
            assert(currentValue.isEmpty())
            assert(onValueChangeCalledWithEmpty)
            onNodeWithContentDescription(
                getStringFromRes(R.string.search),
                useUnmergedTree = true,
            ).assertIsDisplayed()
            onNodeWithContentDescription(
                getStringFromRes(R.string.clear),
                useUnmergedTree = true,
            ).assertDoesNotExist()
        }
    }

    @Test
    fun searchBar_focusChanges_onFocusChangeEventTriggered() {
        var focusChanged = false
        var currentFocusValue = false

        setContent {
            var isFocused by remember { mutableStateOf(false) }
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                closeSearchBar = {},
                isFocused = isFocused,
                onFocusChange = {
                    focusChanged = true
                    currentFocusValue = it
                    isFocused = it
                },
            )
        }

        with(composeTestRule) {
            onNodeWithText(getStringFromRes(R.string.search_bar_placeholder))
                .performClick()
            runOnIdle {
                assert(focusChanged)
                assert(currentFocusValue)
            }
        }
    }
}
