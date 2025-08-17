package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.ui.theme.ChargingHubTheme
import com.akashg.androidapp.charginghub.ui.theme.customColors
import com.akashg.androidapp.charginghub.utils.StandardPreview

@Composable
fun MapLocationDetailsCard(
    modifier: Modifier = Modifier,
    title: String,
    distance: String,
    callAction: () -> Unit = {},
    navigateAction: () -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColors.whiteColor,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .wrapContentSize()
                        .size(97.dp)
                        .clip(RoundedCornerShape(5.dp)),
                ) {
                    Image(
                        painter = painterResource(R.drawable.place_holder),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.customColors.textDark),
                        contentScale = ContentScale.FillBounds,
                    )
                    InfoBadge(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = stringResource(R.string.available),
                        backgroundColor = MaterialTheme.customColors.greenColor,
                        borderColor = MaterialTheme.customColors.greenColor,
                        textColor = MaterialTheme.customColors.whiteColor,
                        padding = PaddingValues(4.dp),
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.customColors.textDark,
                        )
                        InfoBadge(
                            icon = R.drawable.icon_fastcharge,
                            text = stringResource(R.string.fast_charge),
                            backgroundColor = MaterialTheme.customColors.orangeColor,
                            textColor = MaterialTheme.customColors.whiteColor,
                            iconColor = MaterialTheme.customColors.whiteColor,
                            padding = PaddingValues(vertical = 0.dp, horizontal = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                    Text(
                        text = stringResource(R.string.address),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.customColors.textDark.copy(.56f),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        InfoBadge(
                            icon = R.drawable.icon_star,
                            text = stringResource(R.string.star),
                            backgroundColor = MaterialTheme.customColors.yellowColor,
                            style = MaterialTheme.typography.bodySmall,
                            textColor = MaterialTheme.customColors.textDark,
                            iconColor = MaterialTheme.customColors.textDark,
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        InfoBadge(
                            icon = R.drawable.icon_time,
                            text = stringResource(R.string.time),
                            backgroundColor = MaterialTheme.customColors.whiteColor,
                            borderColor = MaterialTheme.customColors.borderColor,
                            style = MaterialTheme.typography.bodySmall,
                            textColor = MaterialTheme.customColors.textDark,
                            iconColor = MaterialTheme.customColors.textDark,
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            LocationInfo(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(5.dp))
                    .background(color = MaterialTheme.customColors.backGroundColor),
                distance,
            )
            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                InfoBadge(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            callAction()
                        },
                    icon = R.drawable.icon_directions,
                    text = stringResource(R.string.contact).uppercase(),
                    backgroundColor = MaterialTheme.customColors.whiteColor,
                    borderColor = MaterialTheme.customColors.blueColor,
                    textColor = MaterialTheme.customColors.blueColor,
                    iconColor = MaterialTheme.customColors.blueColor,
                    iconSize = DpSize(20.dp, 20.dp),
                )

                InfoBadge(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navigateAction()
                        },
                    icon = R.drawable.icon_directions,
                    text = stringResource(R.string.navigate).uppercase(),
                    backgroundColor = MaterialTheme.customColors.blueColor,
                    borderColor = MaterialTheme.customColors.blueColor,
                    textColor = MaterialTheme.customColors.whiteColor,
                    iconColor = MaterialTheme.customColors.whiteColor,
                    iconSize = DpSize(20.dp, 20.dp),
                )
            }
        }
    }
}

@StandardPreview
@Composable
private fun MapLocationDetailsCardPreview() {
    ChargingHubTheme {
        MapLocationDetailsCard(modifier = Modifier.fillMaxWidth(), title = "shakthi bhavan", "10")
    }
}
