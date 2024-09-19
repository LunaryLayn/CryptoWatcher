package com.swissborg.cryptowatcher.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.swissborg.cryptowatcher.ui.theme.CryptoWatcherTypography
import com.swissborg.cryptowatcher.ui.theme.errorContainerLight
import com.swissborg.cryptowatcher.ui.theme.errorLight

@Composable
fun ExpandableErrorLabel(error: String?) {

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(error) {
        expanded = error != null
    }

    AnimatedVisibility(
        visible = expanded,
        enter = expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        if (error != null) {
            Text(
                error,
                color = errorLight,
                style = CryptoWatcherTypography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        errorContainerLight
                    )
            )
        }
    }
}