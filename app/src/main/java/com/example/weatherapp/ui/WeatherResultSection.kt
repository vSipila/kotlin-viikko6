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
import com.example.weatherapp.data.model.WeatherEntity

@Composable
fun WeatherResultSection(weather: WeatherEntity) {
    Card(
        modifier = Modifier.fillMaxWidth()

    ) {

        Column(
            modifier = Modifier.padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(weather.cityName, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${weather.temperature}°C",
                style = MaterialTheme.typography.displayMedium
            )

            Text(weather.description)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Tuntuu kuin: ${weather.feelsLike}°C")
            Text("Kosteus: ${weather.humidity}%")
        }
    }

}