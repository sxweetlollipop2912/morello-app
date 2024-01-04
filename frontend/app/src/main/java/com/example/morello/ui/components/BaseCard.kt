package com.example.morello.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> BaseCard(
    title: @Composable () -> Unit,
    items: List<T>,
    getId: (T) -> Int,
    builder: @Composable (T) -> Unit,
    onSeeIndividual: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onSeeAll: () -> Unit = {},
    seeAllDisabled: Boolean = false,
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = modifier,
    ) {
        Column {
            val horizontalPaddingValue = 15.dp
            val verticalPaddingValue = 8.dp
            Box(
                modifier = Modifier.padding(
                    top = verticalPaddingValue,
                    start = horizontalPaddingValue,
                    end = horizontalPaddingValue,
                )
            ) {
                title()
            }

            Spacer(modifier = Modifier.height(verticalPaddingValue))
            HorizontalDivider()

            items.forEach { item ->
                HorizontalDivider()

                Box(
                    modifier = Modifier
                        .clickable(onClick = { onSeeIndividual(getId(item)) })
                        .padding(
                            horizontal = horizontalPaddingValue,
                            vertical = verticalPaddingValue
                        )
                ) {
                    builder(item)
                }
            }

            if (!seeAllDisabled) {
                HorizontalDivider()
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .clickable(onClick = onSeeAll)
                        .fillMaxWidth()
                        .padding(
                            horizontal = horizontalPaddingValue,
                            vertical = verticalPaddingValue
                        )
                ) {
                    Text(
                        text = "See all",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                    )
                }
            }
        }
    }
}