package restaurant.study.com.test_weather

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by jc_chu on 2018. 08. 05..
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_bt.setOnClickListener {
            startActivity(Intent(this, GpsActivity::class.java))
        }
    }
}
