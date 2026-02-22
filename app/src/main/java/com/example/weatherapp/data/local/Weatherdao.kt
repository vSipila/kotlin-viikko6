package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_cache WHERE cityName = :city")

    fun getWeatherByCity(city: String): Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_cache ORDER BY timestamp DESC")
    fun getAllWeatherHistory(): Flow<List<WeatherEntity>>

}