package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.data.model.WeatherEntity
import com.example.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class WeatherUiState(
    val city: String = "",
    val weather: WeatherEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)



class WeatherViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: WeatherRepository
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {

        val dao = AppDatabase.getDatabase(application).weatherDao()
        repository = WeatherRepository(dao)
    }

    fun onCityChange(city: String) {
        _uiState.value = _uiState.value.copy(city = city)

    }


    fun fetchWeather() {

        val city = _uiState.value.city
        if (city.isBlank()) return

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)


        viewModelScope.launch {

            repository.getWeatherFromCache(city).collect { cached ->
                if (cached != null && System.currentTimeMillis() - cached.timestamp < 30 * 60 * 1000) {
                    _uiState.value = _uiState.value.copy(weather = cached, isLoading = false)
                } else {
                    val result = repository.fetchAndCacheWeather(city)

                    if (result.isSuccess) {

                        _uiState.value = _uiState.value.copy(
                            weather = result.getOrNull(),
                            isLoading = false
                        )

                    } else {

                        _uiState.value = _uiState.value.copy(
                            error = "Kaupunkia ei löydy: ${result.exceptionOrNull()?.message}",
                            isLoading = false
                        )
                    }

                }
            }
        }

    }
}