package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WeatherUiState(
    val city: String = "",
    val weather: WeatherResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null

)


class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun onCityChange(city: String) {

        _uiState.value = _uiState.value.copy(city = city)

    }


    fun fetchWeather() {

        val city = _uiState.value.city
        if (city.isBlank()) return



        _uiState.value = _uiState.value.copy(isLoading = true, error = null, weather = null)

        viewModelScope.launch {
            try {


                val result = RetrofitInstance.api.getWeather(city, "2c7e996c44a721b8e5cdecd979f3fd74")
                _uiState.value = _uiState.value.copy(weather = result, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Kaupunkia ei löydy: ${e.message}",
                    isLoading = false
                )


            }
        }
    }
}
