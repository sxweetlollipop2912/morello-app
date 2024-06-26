package com.example.morello.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MorelloTopBar(
    head: @Composable () -> Unit,
    tail: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(Modifier.padding(top = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            head()
            tail()
        }
        Spacer(modifier = Modifier.padding(8.dp))
        HorizontalDivider()
    }
}