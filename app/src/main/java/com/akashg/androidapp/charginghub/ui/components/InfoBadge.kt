package com.akashg.androidapp.charginghub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.ui.theme.ChargingHubTheme
import com.akashg.androidapp.charginghub.ui.theme.customColors

@Composable
fun InfoBadge(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    iconDescription: String? = null,
    text: String,
    backgroundColor: Color = MaterialTheme.customColors.whiteColor,
    borderColor: Color = Color.Unspecified,
    textColor: Color = Color.Unspecified,
    iconColor: Color = Color.Unspecified,
    padding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 10.dp),
    style: TextStyle = TextStyle.Default,
    iconSize: DpSize = DpSize(16.dp, 16.dp),
) {
    Row(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(50))
            .border(
                1.dp,
                borderColor,
                RoundedCornerShape(50),
            )
            .padding(padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        icon?.let {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .padding(end = 4.dp),
                painter = painterResource(it),
                contentDescription = iconDescription,
                tint = iconColor,
            )
        }
        Text(text = text, color = textColor, style = style)
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingOrTimingViewPreview() {
    ChargingHubTheme {
        Column(Modifier.padding(16.dp)) {
            InfoBadge(
                icon = R.drawable.icon_star,
                text = "4.2",
                backgroundColor = MaterialTheme.customColors.yellowColor,
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoBadge(
                icon = R.drawable.icon_time,
                text = "10:00 AM - 10:00 PM",
                backgroundColor = MaterialTheme.customColors.whiteColor,
                borderColor = MaterialTheme.customColors.borderColor,
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoBadge(
                icon = R.drawable.icon_fastcharge,
                text = "Fast Charging",
                backgroundColor = MaterialTheme.customColors.orangeColor,
                textColor = MaterialTheme.customColors.whiteColor,
                iconColor = MaterialTheme.customColors.whiteColor,
            )

            Spacer(modifier = Modifier.height(16.dp))
            InfoBadge(
                modifier = Modifier.fillMaxWidth(),
                text = "Busy",
                backgroundColor = MaterialTheme.customColors.redColor,
                borderColor = MaterialTheme.customColors.redColor,
                textColor = MaterialTheme.customColors.whiteColor,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
