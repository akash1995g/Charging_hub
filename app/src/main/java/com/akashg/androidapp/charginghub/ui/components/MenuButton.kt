package com.akashg.androidapp.charginghub.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.ui.theme.customColors

@Composable
fun MenuButton(@DrawableRes iconId: Int, contentDescription: String? = null, onClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .size(50.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.customColors.borderColor,
                shape = CircleShape,
            ),
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.customColors.whiteColor,
        ),
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconId),
            contentDescription = contentDescription,
            tint = Color.Unspecified,
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun MenuButtonPreview() {
    MenuButton(R.drawable.icon_gps_fixed) {}
}
