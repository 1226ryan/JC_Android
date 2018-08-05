package restaurant.study.com.test_weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.BitmapFactory
import android.os.Handler
import android.widget.ImageView
import java.net.URL

/**
 * Created by jc_chu on 2018. 08. 05..
 */

class MainActivity : AppCompatActivity() {
    private lateinit var stringWeather: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppClient.getInstance().getWeatherCurrent(object: ResultCallback<JsonObject>{
            override fun onSuccess(t: JsonObject?) {
                Log.i("getWeatherCurrent", "onSuccess t = $t")
                if(t != null) {
                    Log.i("getWeatherCurrent", "name = ${t.get("name").asString}")
                    Log.i("getWeatherCurrent", "main = ${t.get("main").asJsonObject.get("temp").asFloat}")
                    Log.i("getWeatherCurrent", "main = ${t.get("weather").asJsonArray.get(0).asJsonObject.get("description").asString}")

                    val cityName = t.get("name").asString
                    val temperature = t.get("main").asJsonObject.get("temp").asFloat-273
                    val description = t.get("weather").asJsonArray.get(0).asJsonObject.get("description").asString
                    val icon = t.get("weather").asJsonArray.get(0).asJsonObject.get("icon").asString
                    stringWeather = "cityName = $cityName \ntemperature = $temperature \ndescription = $description"

                    setImageView("http://openweathermap.org/img/w/$icon.png")
                    main_tv.text = stringWeather
                }
            }

            override fun onFailure(t: Throwable) {
                Log.e("getWeatherCurrent", "onFailure t = ${t.printStackTrace()}")
            }
        })
    }

    private val handler = Handler()
    private fun setImageView(stringUrl: String) {
        val t = Thread(Runnable {
            try {
                val imageView = findViewById<ImageView>(R.id.main_iv)
                val url = URL(stringUrl)
                val inputStream = url.openStream()
                val bitmapFactory = BitmapFactory.decodeStream(inputStream)

                handler.post {
                    imageView.setImageBitmap(bitmapFactory)
                }
                imageView.setImageBitmap(bitmapFactory)
            } catch (e: Exception) {

            }
        })
        t.start()
    }
}
