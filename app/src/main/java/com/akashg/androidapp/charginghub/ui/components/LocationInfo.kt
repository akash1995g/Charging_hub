package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.ui.theme.ChargingHubTheme
import com.akashg.androidapp.charginghub.ui.theme.customColors
import com.akashg.androidapp.charginghub.utils.StandardPreview

@Composable
fun LocationInfo(modifier: Modifier = Modifier, distance: String) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_jeeto),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            Text(
                stringResource(R.string.vehicle_type),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.customColors.textDark,
            )
        }
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 8.dp),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_ccs),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            Text(
                stringResource(R.string.ccs_type),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.customColors.textDark,
            )
        }
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 8.dp),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = distance,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.customColors.blueColor,
            )
            Text(
                text = stringResource(R.string.distance_unit),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.customColors.textDark,
            )
        }
    }
}

@StandardPreview
@Composable
private fun LocationInfoPreview() {
    ChargingHubTheme {
        LocationInfo(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            distance = "10",
        )
    }
}
