package com.swissborg.cryptowatcher.ui.features.mainScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.swissborg.cryptowatcher.R
import com.swissborg.cryptowatcher.ui.components.ExpandableErrorLabel
import com.swissborg.cryptowatcher.ui.theme.ExtendedColorScheme
import com.swissborg.cryptowatcher.ui.theme.errorLight
import com.swissborg.cryptowatcher.ui.theme.onErrorLight
import com.swissborg.cryptowatcher.ui.theme.primaryLight
import com.swissborg.domain.model.TickerModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()

    val tickers by viewModel.tickers.collectAsState()
    val error by viewModel.error.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }, actions = {
                if (isConnected) {
                    Text(text = stringResource(id = R.string.connected), color = primaryLight)
                } else {
                    Text(text = stringResource(id = R.string.no_internet), color = errorLight)
                }
                PulsatingDot(color = if (isConnected) primaryLight else errorLight)
            })
        })
    { paddingValues ->

        if (tickers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            ExpandableErrorLabel(error)

            LazyColumn() {
                items(tickers) { ticker ->
                    TickerItem(ticker)
                }
            }
        }
    }
}

@Composable
fun TickerItem(ticker: TickerModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = ticker.symbol)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = ticker.lastPrice.toString())
    }
}

@Composable
fun PulsatingDot(
    modifier: Modifier = Modifier,
    color: Color = Color.Green,  // Puedes cambiar el color si lo deseas
    minRadius: Float = 15f,      // Radio mínimo del punto
    maxRadius: Float = 20f       // Radio máximo del punto (cuando está en su punto máximo)
) {
    // Animación infinita
    val infiniteTransition = rememberInfiniteTransition()

    // Animamos el radio del punto desde `minRadius` a `maxRadius`
    val radius by infiniteTransition.animateFloat(
        initialValue = minRadius,
        targetValue = maxRadius,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse  // Reverso para crear el efecto de pulsar
        )
    )

    // Dibujamos el punto usando `Canvas`
    Canvas(modifier = modifier.size(40.dp)) {
        drawCircle(
            color = color,    // Color del punto
            radius = radius   // Radio animado del punto
        )
    }
}