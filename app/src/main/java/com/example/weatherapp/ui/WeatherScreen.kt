package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text("Sääsovellus", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.city,
            onValueChange = { viewModel.onCityChange(it) },
            label = { Text("Kaupunki") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(8.dp))

        Button(

            onClick = { viewModel.fetchWeather() },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Hae sää")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when {

            uiState.isLoading -> CircularProgressIndicator()
            uiState.error != null -> Text(
                text = uiState.error!!,
                color = MaterialTheme.colorScheme.error
            )
            uiState.weather != null -> WeatherResultSection(uiState.weather!!)
        }

    }
}
