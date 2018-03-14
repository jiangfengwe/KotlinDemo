package com.zhongwenhui.tdin360.kotlindemo


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.zhongwenhui.tdin360.kotlindemo.domain.commands.RequestForecastCommand
import com.zhongwenhui.tdin360.kotlindemo.domain.model.ForecastList
import com.zhongwenhui.tdin360.kotlindemo.extensions.DelegatesExt
import com.zhongwenhui.tdin360.kotlindemo.adapters.ForecastListAdapter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(),ToolbarManager{
    private val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE, SettingsActivity.DEFAULT_ZIP)//DelegatesExtde 扩展
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        //forecastList.layoutManager=GridLayoutManager(this,3)
        attachToScroll(forecastList)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() = async(UI) {
        //toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
        val result = bg { RequestForecastCommand(zipCode).execute() }
        updateUI(result.await())
    }

    private fun updateUI(weekForecast: ForecastList) {
        /*val adapter=ForecastListAdapter(weekForecast){
            toast("${weekForecast.size}")
        }*/

        val adapter = ForecastListAdapter(weekForecast) {
          /*  val intent = Intent()
            //获取intent对象
            intent.setClass(this,AAActivity::class.java)
            startActivity(intent)*/
            //startActivity(Intent(this, DetailActivity::class.java))

            startActivity<DetailActivity>(DetailActivity.ID to it.id,DetailActivity.CITY_NAME to weekForecast.city)
        }
        forecastList.adapter = adapter
        toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
    }

}
