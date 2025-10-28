package com.example.temperaturedashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.temperaturedashboard.ui.theme.TemperatureDashboardTheme

// MainActivity hosting the Compose UI and providing the ViewModel
class MainActivity : ComponentActivity() {
    private val viewModel: TemperatureViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TemperatureDashboardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Main screen composable taking the ViewModel as parameter
                    TemperatureDashboardScreen(viewModel)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureDashboardScreen(viewModel: TemperatureViewModel) {
    // Collect temperature readings and running state from StateFlows in ViewModel
    val readings by viewModel.temperatures.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()

    // Calculate current, average, min, and max temperature values from reading
    val currentTemp = readings.lastOrNull()?.value ?: 0f
    val avgTemp = if (readings.isNotEmpty()) readings.map { it.value }.average().toFloat() else 0f
    val minTemp = readings.minOfOrNull { it.value } ?: 0f
    val maxTemp = readings.maxOfOrNull { it.value } ?: 0f

    Scaffold(
        topBar = { TopAppBar(title = { Text("Temperature Dashboard") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display current, average, min, max temperatures
            Text("Current: %.1f°F".format(currentTemp), style = MaterialTheme.typography.headlineMedium)
            Text("Average: %.1f°F | Min: %.1f°F | Max: %.1f°F".format(avgTemp, minTemp, maxTemp))

            Spacer(modifier = Modifier.height(16.dp))

            // Chart visualization of temperature values
            TemperatureChart(readings.map { it.value })

            Spacer(modifier = Modifier.height(16.dp))

            // Button to pause/resume temperature updates
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Button(onClick = { viewModel.toggleUpdates() }) {
                    Text(if (isRunning) "Pause" else "Resume")
                }
            }

            // LazyColumn showing the list of temperature readings with timestamps
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                reverseLayout = true, // Reverse the order to show newest at the top
            ) {
                items(readings) { reading ->
                    Text("${reading.timestamp} — %.1f°F".format(reading.value))
                }
            }
        }
    }
}

// Composable to draw a simple line chart of temperature values using Canvas
@Composable
fun TemperatureChart(values: List<Float>) {
    if (values.size < 2) return
    androidx.compose.foundation.Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)) {
        val spaceX = size.width / (values.size - 1) // horizontal spacing between points
        val maxY = values.maxOrNull() ?: 0f
        val minY = values.minOrNull() ?: 0f
        // Calculate scale factor to map temperature range to canvas height
        val scale = if (maxY - minY == 0f) 1f else size.height / (maxY - minY)

        // Draw lines between consecutive temperature points
        for (i in 1 until values.size) {
            drawLine(
                color = androidx.compose.ui.graphics.Color.Green,
                start = androidx.compose.ui.geometry.Offset((i - 1) * spaceX, size.height - (values[i - 1] - minY) * scale),
                end = androidx.compose.ui.geometry.Offset(i * spaceX, size.height - (values[i] - minY) * scale),
                strokeWidth = 4f
            )
        }
    }
}
