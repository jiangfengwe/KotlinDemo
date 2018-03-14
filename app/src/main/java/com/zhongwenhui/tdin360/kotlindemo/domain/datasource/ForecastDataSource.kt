package com.zhongwenhui.tdin360.kotlindemo.domain.datasource

import com.zhongwenhui.tdin360.kotlindemo.domain.model.Forecast
import com.zhongwenhui.tdin360.kotlindemo.domain.model.ForecastList

interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?

}