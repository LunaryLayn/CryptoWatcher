/*
 * © Hugo 2024 for SwissBorg technical challenge
 */

package com.swissborg.cryptowatcher.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.swissborg.cryptowatcher.ui.theme.CryptoWatcherTypography

@Composable
fun ExpandableErrorLabel(error: String?) {

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(error) {
        expanded = error != null
    }
    AnimatedVisibility(
        visible = expanded, enter = expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        if (error != null) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = CryptoWatcherTypography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}