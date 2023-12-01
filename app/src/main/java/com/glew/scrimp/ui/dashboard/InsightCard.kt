package com.glew.scrimp.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.glew.scrimp.ui.spending.InsightCardType

@Composable
fun InsightCard(
    modifier: Modifier = Modifier,
    type: InsightCardType
) {
    Card(
        modifier = Modifier
            .width(152.dp)
            .height(160.dp)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(8.dp)
        ) {
            Icon(
                imageVector = type.getIcon(),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = type.getTitle(),
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = type.getBody(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}