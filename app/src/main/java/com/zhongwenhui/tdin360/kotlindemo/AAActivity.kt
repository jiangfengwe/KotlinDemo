package com.zhongwenhui.tdin360.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.zhongwenhui.tdin360.kotlindemo.domain.commands.RequestDayForecastCommand
import com.zhongwenhui.tdin360.kotlindemo.domain.model.Forecast
import com.zhongwenhui.tdin360.kotlindemo.extensions.color
import com.zhongwenhui.tdin360.kotlindemo.extensions.textColor
import com.zhongwenhui.tdin360.kotlindemo.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find
import java.text.DateFormat

class AAActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aa)
        initToolbar()

        toolbarTitle = intent.getStringExtra(DetailActivity.CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        async(UI) {
            val result = bg { RequestDayForecastCommand(intent.getLongExtra(DetailActivity.ID, -1)).execute() }
            bindForecast(result.await())
        }
    }
    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(ctx).load(iconUrl).into(icon)
        toolbar.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}º"
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }
}
