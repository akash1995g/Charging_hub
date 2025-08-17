package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.ui.theme.ChargingHubTheme
import com.akashg.androidapp.charginghub.ui.theme.customColors

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    closeSearchBar: () -> Unit,
    isFocused: Boolean = false,
    onFocusChange: (Boolean) -> Unit = {},
) {
    OutlinedTextField(
        modifier =
        modifier.onFocusEvent(
            {
                onFocusChange(it.isFocused)
            },
        ),
        shape = RoundedCornerShape(50.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = stringResource(R.string.search_bar_placeholder),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.customColors.textDark.copy(alpha = if (isFocused) .5f else .72f),
            )
        },
        prefix = {
            val icon = if (isFocused) {
                R.drawable.icon_arrow_left
            } else {
                R.drawable.icon_search
            }
            val description = if (isFocused) {
                stringResource(R.string.back)
            } else {
                stringResource(R.string.search)
            }
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .clickable(enabled = isFocused) {
                        closeSearchBar()
                    },
                painter = painterResource(icon),
                contentDescription = description,
            )
        },
        suffix = {
            if (isFocused) {
                val icon = if (value.isNotEmpty()) {
                    R.drawable.icon_close
                } else {
                    R.drawable.icon_search
                }
                val description = if (value.isNotEmpty()) {
                    stringResource(R.string.clear)
                } else {
                    stringResource(R.string.search)
                }
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(enabled = value.isNotEmpty()) {
                            onValueChange("")
                        },
                    painter = painterResource(icon),
                    contentDescription = description,
                )
            } else {
                null
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.customColors.secondaryBackGroundColor,
            unfocusedBorderColor = MaterialTheme.customColors.secondaryBackGroundColor,
            focusedTextColor = MaterialTheme.customColors.textDark,
            unfocusedTextColor = MaterialTheme.customColors.textDark,
            errorContainerColor = MaterialTheme.customColors.whiteColor,
            focusedContainerColor = MaterialTheme.customColors.whiteColor,
            unfocusedContainerColor = MaterialTheme.customColors.whiteColor,
            errorBorderColor = MaterialTheme.customColors.whiteColor,
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    ChargingHubTheme {
        SearchBar(
            value = "",
            onValueChange = {},
            closeSearchBar = {},
            isFocused = true,
        )
    }
}
