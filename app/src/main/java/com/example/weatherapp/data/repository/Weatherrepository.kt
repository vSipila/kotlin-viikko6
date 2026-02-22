package com.example.weatherapp.data.repository

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.model.WeatherEntity
import com.example.weatherapp.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class WeatherRepository(private val weatherDao: WeatherDao) {


    fun getWeatherFromCache(city: String): Flow<WeatherEntity?> {

        return weatherDao.getWeatherByCity(city)
    }

    suspend fun fetchAndCacheWeather(city: String): Result<WeatherEntity> {

        return try {

            val response = RetrofitInstance.api.getWeather(city, "2c7e996c44a721b8e5cdecd979f3fd74")
            val entity = WeatherEntity(
                cityName = response.name,
                temperature = response.main.temp,
                description = response.weather.firstOrNull()?.description ?: "",
                feelsLike = response.main.feels_like,
                humidity = response.main.humidity,
                timestamp = System.currentTimeMillis()
            )
            weatherDao.insertWeather(entity)
            Result.success(entity)
        } catch (e: Exception) {

            Result.failure(e)
        }
    }



    fun getAllHistory(): Flow<List<WeatherEntity>> {
        return weatherDao.getAllWeatherHistory()
    }

}