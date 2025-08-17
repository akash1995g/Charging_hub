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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.ui.theme.ChargingHubTheme
import com.akashg.androidapp.charginghub.ui.theme.customColors
import com.akashg.androidapp.charginghub.utils.StandardPreview

@Composable
fun MenuLocationDetailsCard(
    modifier: Modifier = Modifier,
    place: String,
    distance: String,
    callAction: () -> Unit = {},
    navigateAction: () -> Unit = {},
) {
    Card(
        modifier,
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(modifier.background(color = MaterialTheme.customColors.whiteColor)) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .size(width = 156.dp, height = 194.dp)
                    .clip(RoundedCornerShape(5.dp)),
            ) {
                Image(
                    painter = painterResource(R.drawable.place_holder),
                    contentDescription = null,
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.place_name),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f, fill = false),
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
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$place - $distance",
                    style = MaterialTheme.typography.bodySmall,
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
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    InfoBadge(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                callAction()
                            },
                        text = stringResource(R.string.contact),
                        backgroundColor = MaterialTheme.customColors.whiteColor,
                        borderColor = MaterialTheme.customColors.blueColor,
                        textColor = MaterialTheme.customColors.blueColor,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    InfoBadge(
                        text = stringResource(R.string.navigate),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navigateAction()
                            },
                        backgroundColor = MaterialTheme.customColors.blueColor,
                        borderColor = MaterialTheme.customColors.blueColor,
                        textColor = MaterialTheme.customColors.whiteColor,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@StandardPreview
@Composable
private fun MenuLocationDetailsCardPreview() {
    ChargingHubTheme {
        MenuLocationDetailsCard(modifier = Modifier.fillMaxWidth(), "place ", distance = "15")
    }
}
