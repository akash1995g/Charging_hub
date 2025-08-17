package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.ui.theme.ChargingHubTheme
import com.akashg.androidapp.charginghub.ui.theme.customColors
import com.akashg.androidapp.charginghub.utils.StandardPreview

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    locationName: String,
    locationAddress: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(28.dp),
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.icon_location),
            contentDescription = null,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = locationName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.customColors.primaryText.copy(.87f),
            )
            Text(
                text = locationAddress,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.customColors.primaryText.copy(.56f),
            )
        }
    }
    HorizontalDivider()
}

@StandardPreview
@Composable
fun SearchItemPreview() {
    ChargingHubTheme {
        SearchItem(locationName = "Location Name", locationAddress = "Location Address")
    }
}
