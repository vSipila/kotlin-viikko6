package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.model.WeatherResponse

@Composable
fun WeatherResultSection(weather: WeatherResponse) {

    Card(
        modifier = Modifier.Companion.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.Companion.padding(16.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {

            Text(weather.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Text(
                "${weather.main.temp}°C",
                style = MaterialTheme.typography.displayMedium
            )

            Text(weather.weather.firstOrNull()?.description ?: "")
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Text("Tuntuu kuin: ${weather.main.feels_like}°C")
            Text("Kosteus: ${weather.main.humidity}%")
        }
    }

}