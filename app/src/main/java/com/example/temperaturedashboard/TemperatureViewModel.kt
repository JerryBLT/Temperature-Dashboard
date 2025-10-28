package com.example.temperaturedashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

// Data class representing a temperature reading with timestamp and value
data class TemperatureReading(val timestamp: String, val value: Float)

class TemperatureViewModel : ViewModel() {
    // MutableStateFlow holding the last 20 temperature readings, exposed as read-only StateFlow
    private val _temperatures = MutableStateFlow<List<TemperatureReading>>(emptyList())
    val temperatures: StateFlow<List<TemperatureReading>> get() = _temperatures

    // MutableStateFlow to track whether temperature updates are running or paused
    private val _isRunning = MutableStateFlow(true)
    val isRunning: StateFlow<Boolean> get() = _isRunning

    // Initialization block starts the simulation coroutine
    init {
        startSimulation()
    }

    // Coroutine to simulate temperature readings every 2 seconds
    private fun startSimulation() {
        viewModelScope.launch {
            while (true) {
                if (_isRunning.value) {
                    val newTemp = Random.nextFloat() * 20 + 65  // Generates 65–85°F
                    val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                    val updated = (_temperatures.value + TemperatureReading(timestamp, newTemp)).takeLast(20)
                    _temperatures.value = updated
                }
                delay(2000L) // Update every 2 seconds
            }
        }
    }

    // Toggle function to pause or resume temperature updates
    fun toggleUpdates() {
        _isRunning.value = !_isRunning.value
    }
}
