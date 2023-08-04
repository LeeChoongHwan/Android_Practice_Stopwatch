package com.example.stopwatch_android_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var startButton : Button
    lateinit var refreshButton : Button
    lateinit var millisecondText : TextView
    lateinit var secondText : TextView
    lateinit var minuiteText : TextView

    private var isRunning = false

    private var timer : Timer? = null
    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.btn_start_pause)
        refreshButton = findViewById(R.id.btn_refresh)
        millisecondText = findViewById(R.id.millisecond_text)
        secondText = findViewById(R.id.second_text)
        minuiteText = findViewById(R.id.minuite_text)

        startButton.setOnClickListener(this)
        refreshButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_start_pause -> {
                if (isRunning) {
                    pause()
                }
                else {
                    start()
                }
            }
            R.id.btn_refresh -> {
                refresh()
            }
        }
    }
    private fun start() {
        startButton.text = getString(R.string.pause_btn)
        isRunning = true

        timer = timer(period = 10) {
            time ++

            val millisecond = time%100
            val second = (time%6000)/100
            val minute = time/6000

            runOnUiThread {
                if (isRunning) {
                    millisecondText.text =
                        if (millisecond < 10) ".0${millisecond}" else ".${millisecond}"
                    secondText.text = if (second < 10) ":0${second}" else ":${second}"
                    minuiteText.text = "$minute"
                }
            }
        }

    }
    private fun pause() {
        startButton.text = getString(R.string.start_btn)
        isRunning = false
        timer?.cancel()
    }

    private fun refresh() {
        startButton.text = getString(R.string.start_btn)
        timer?.cancel()
        isRunning = false

        time = 0
        millisecondText.text = getString(R.string.millisecond_text)
        secondText.text = getString(R.string.second_text)
        minuiteText.text = getString(R.string.minuite_text)
    }

}