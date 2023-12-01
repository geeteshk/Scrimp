package com.glew.scrimp.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.glew.scrimp.ui.common.AutoSizeText

@Composable
fun DashboardInsightCard(
    modifier: Modifier,
    icon: ImageVector,
    label: String,
    body: String
) {
    Card(
        modifier = Modifier
            .height(72.dp)
            .then(modifier)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(imageVector = icon, contentDescription = null)

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall
                    )

                    AutoSizeText(
                        text = body,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}