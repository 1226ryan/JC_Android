package com.example.administrator.test_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonClick()
        println("----------------------------------------")

        RxJavaTest()
        println("----------------------------------------")

        var str = "name"
        val name = if(str.length > 3) str else "Kotlin"
        println("Check str : ${name}")
        println("Check str : $name 입니다")
        println("----------------------------------------")

        println("Check str2 : ${if(str.length < 3) str else "Kotlin"}")
        println("----------------------------------------")

        val rectangle = CustomProperty(20, 20)
        println("rectangle.isSquare : ${rectangle.isSquare}")
        println("----------------------------------------")

        println("createRandomrectangle() : ${createRandomrectangle()}")
        println("----------------------------------------")

        println("Color.BLUE.rgb() : ${Color.BLUE.rgb()}")
        println("----------------------------------------")

        println("getMnemonic(Color.BLUE) : ${getMnemonic(Color.BLUE)}")
        println("----------------------------------------")

        println("mix(BLUE, YELLOW) : ${mix(Color.BLUE, Color.YELLOW)}")
        println("mix(YELLOW, BLUE) : ${mix(Color.YELLOW, Color.BLUE)}")
        println("----------------------------------------")

        println("mixOptimized(BLUE, YELLOW) : ${mixOptimized(Color.BLUE, Color.VIOLET)}")
        println("----------------------------------------")

        println("eval(Sum(Sum(Num(1), Num(2)), Num(4))) : ${eval(Sum(Sum(Num(1), Num(2)), Num(4)))}")
        println("----------------------------------------")

        println("evalWhen(Sum(Sum(Num(2), Num(4)), Num(8))) : ${evalWhen(Sum(Sum(Num(2), Num(4)), Num(8)))}")
        println("----------------------------------------")

        println("evalWithLogging(Sum(Sum(Num(3), Num(6)), Num(12))) : ${evalWithLogging(Sum(Sum(Num(3), Num(6)), Num(12)))}")
        println("----------------------------------------")

        println("sum_______ = " + sum(1, 5))
        println("comparison = " + comparison(3, 4))
        println("getStringLength : " + getStringLength("Hello world"))
        println("getStringLength : " + getStringLength(0))
        println("listTest() : " + listTest())
        println("getListOf() : "+getListOf())
        println("getWhile() : "+getWhile())
        println("getWhen() : " + getWhen())
        println("getRanges() : " + getRanges())
    }

    fun buttonClick() {
        btn_submit.setOnClickListener {
            tv_message.text = "Hello, " + et_name.text.toString()
        }

        val cities = listOf("Seoul", "Tokyo", "Mountain View", "Singapore")
        btn_test_1.setOnClickListener {
            selector(title = "Select City", items = cities) { _, selection ->
                toast("You selected ${cities[selection]}!")
            }
        }

        btn_test_2.setOnClickListener {
            val pd = progressDialog(title = "File Download", message = "Downloading...")
            pd.show()
            pd.progress = 50
        }

        btn_test_3.setOnClickListener {
            indeterminateProgressDialog(message = "Please wait...").show()
        }

        btn_test_4.setOnClickListener {
            alert(title = "Message", message = "Let's learn Kotlin!") {
                positiveButton("Yes") {
                    toast("Yay!")
                }
                negativeButton("No") {
                    longToast("No way....")
                }
            }.show()
        }

        btn_test_5.setOnClickListener {
            startActivity<test>()
        }

        btn_test_6.setOnClickListener {
            val sampleVal: SampleVal = SampleVal("Name", "c@c.com")
            println("sampleVal.name : "+sampleVal.name)
            println("sampleVal.email : "+sampleVal.email)
            println("-----------------------------------------------")

            val sampleVar: SampleVar = SampleVar("Name", "c@c.com")
            println("SampleVar.name : "+sampleVar.name)
            println("SampleVar.email : "+sampleVar.email)
            sampleVar.name = "Change"
            sampleVar.email = "e@e.com"
            println("SampleVar.name : "+sampleVar.name)
            println("SampleVar.email : "+sampleVar.email)
            println("-----------------------------------------------")

            val sample: Sample = Sample()
            println("sample.name : "+sample.name)
            println("sample.email : "+sample.email)
            sample.name = "Name"
            sample.email = "e@e.com"
            println("sample.name : "+sample.name)
            println("sample.email : "+sample.email)
            println("-----------------------------------------------")

            println("filterTest() : "+filterTest())
        }

        btn_test_7.setOnClickListener {
            val sample_lazy_lateinit : Sample_lazy_lateinit = Sample_lazy_lateinit()
            sample_lazy_lateinit.test()

            println("-----------------------------------------------")

            printProduct("15", "3")
        }
    }
}

