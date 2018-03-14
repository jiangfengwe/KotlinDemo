package com.zhongwenhui.tdin360.kotlindemo.data.server

import com.zhongwenhui.tdin360.kotlindemo.data.db.ForecastDb
import com.zhongwenhui.tdin360.kotlindemo.domain.datasource.ForecastDataSource
import com.zhongwenhui.tdin360.kotlindemo.domain.model.ForecastList

class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}