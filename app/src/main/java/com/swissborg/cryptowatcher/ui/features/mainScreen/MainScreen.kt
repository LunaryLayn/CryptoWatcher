package com.swissborg.cryptowatcher.ui.features.mainScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.swissborg.cryptowatcher.R
import com.swissborg.cryptowatcher.ui.components.ExpandableErrorLabel
import com.swissborg.cryptowatcher.ui.theme.secondaryContainerLight
import com.swissborg.cryptowatcher.ui.theme.secondaryLight
import com.swissborg.cryptowatcher.util.Util.formatNumber
import com.swissborg.domain.model.TickerModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()

    val tickers by viewModel.tickers.collectAsState()
    val error by viewModel.error.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Estado para la criptomoneda seleccionada
    var selectedTicker by remember { mutableStateOf<TickerModel?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }, actions = {
                if (isConnected) {
                    Text(text = stringResource(id = R.string.connected), color = MaterialTheme.colorScheme.primary)
                } else {
                    Text(text = stringResource(id = R.string.no_internet), color = MaterialTheme.colorScheme.error)
                }
                PulsatingDot(color = if (isConnected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
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
        } else {

            selectedTicker = tickers.find { it.symbol == selectedTicker?.symbol } ?: tickers.first()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text(text = "Search") },
                    modifier = Modifier.fillMaxWidth()
                )
                // Mostrar la tarjeta de la criptomoneda seleccionada
                SelectedCryptoCard(selectedTicker!!)


                ExpandableErrorLabel(error)

                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(vertical = 16.dp))

                LazyColumn() {
                    items(tickers.filter { it.symbol.contains(searchQuery.text, ignoreCase = true) }) { ticker ->
                        TickerItem(ticker = ticker, isSelected = ticker == selectedTicker) {
                            selectedTicker = ticker
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TickerItem(ticker: TickerModel, isSelected: Boolean, onClick: () -> Unit) {
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .let {
                // Si está seleccionado, aplicar un borde de color primary
                if (isSelected) it.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                else it
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column() {
                Text(text = ticker.symbol, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text(text = "${ticker.lastPrice.formatNumber()} US$", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.tertiary)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    PriceIncreaseTriangleAnimation()
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${ticker.dailyChangePerc.formatNumber()}%")
                }
            }
        }
    }
}

@Composable
fun SelectedCryptoCard(ticker: TickerModel) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = ticker.symbol, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Text(text = "Price: ${ticker.lastPrice}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.tertiary)
            Text(text = "Daily change ${ticker.dailyChangePerc}%", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
            Row {
                Text(text = "TRON")
                Text(
                    text = "TRX", color = secondaryLight, modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(secondaryContainerLight)
                )
            }
        }
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

@Composable
fun PriceIncreaseTriangleAnimation(modifier: Modifier = Modifier) {
    // Definimos la altura del movimiento hacia arriba
    val animationHeight = 20f

    // Definimos la animación con un ciclo repetido
    val infiniteTransition = rememberInfiniteTransition()
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = -animationHeight, // Moverse hacia arriba
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(10.dp)
        ) {
            // Aplicar la traslación para mover el triángulo
            translate(left = 0f, top = offsetY) {
                drawTriangle()
            }
        }
    }
}

fun DrawScope.drawTriangle() {
    val trianglePath = Path().apply {
        moveTo(size.width / 2, 0f) // Punto superior del triángulo
        lineTo(0f, size.height) // Esquina inferior izquierda
        lineTo(size.width, size.height) // Esquina inferior derecha
        close() // Cerrar el triángulo
    }

    drawPath(
        path = trianglePath,
        color = Color.Green // Color del triángulo
    )
}