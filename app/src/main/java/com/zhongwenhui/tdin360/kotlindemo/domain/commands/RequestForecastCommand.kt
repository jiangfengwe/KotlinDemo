package com.zhongwenhui.tdin360.kotlindemo.domain.commands

import com.zhongwenhui.tdin360.kotlindemo.domain.datasource.ForecastProvider
import com.zhongwenhui.tdin360.kotlindemo.domain.model.ForecastList

class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, DAYS)
}