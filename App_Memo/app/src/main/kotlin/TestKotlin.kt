import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

class TestKotlin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun printKotlin():Unit {
        println("Hello Kotlin")
    }
    fun printKotlin2() {
        println("Hello Kotlin")
    }

    var a = 1
}