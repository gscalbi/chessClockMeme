package com.example.chessclockmeme

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.chessclockmeme.databinding.ActivityMainBinding



lateinit var mBinding : ActivityMainBinding
const val topInitialTime: Long = 300
var topTimeLeft = topInitialTime
const val bottomInitialTime: Long = 300
var bottomTimeLeft = bottomInitialTime
const val oneSecondInMilliseconds: Long =  1000
var topClicked = false
var bottomClicked= false
var tiempoAMostrar1 = "00:00"
var tiempoAMostrar2= "00:00"




class MainActivity : AppCompatActivity() {

    // This is a variable to hold a special timer object. We set it here once so that it is
    // available throughout the program. It will be reset when the buttons are pressed though.
    private var topTimer: CountDownTimer = object : CountDownTimer(0, 0) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {}
    }

    private var bottomTimer: CountDownTimer = object : CountDownTimer(0, 0) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        tiempoAMostrar1 = segToMin(topInitialTime)
        tiempoAMostrar2 = segToMin(bottomInitialTime)
        mBinding.tvP1.text = tiempoAMostrar1
        mBinding.tvP2.text = tiempoAMostrar2
        mBinding.tvP1.setBackgroundColor(Color.LTGRAY)
        mBinding.tvP2.setBackgroundColor(Color.LTGRAY)


        mBinding.tvP1.setOnClickListener {

            //make sure bottom button is enabled
            mBinding.tvP2.isEnabled = true
            mBinding.tvP2.isClickable = true


            mBinding.ibReset.visibility = View.INVISIBLE
            mBinding.ibSettings.visibility = View.INVISIBLE

            //set up timer that will run when the top button is clicked
            bottomTimer = object :
                CountDownTimer(bottomTimeLeft * oneSecondInMilliseconds, oneSecondInMilliseconds) {
                // This is a special function that is part of the timer. It runs every time the
                // timer's interval passes. In this case, it is set to run every second.
                override fun onTick(millisUntilFinished: Long) {
                    // Update the label to show the remaining time and then remove one second from
                    // the timeLeft variable so that if the timer has to be stopped and started
                    // again, we know how much time to put on the new timer.
                    //mBinding.tvP2.text = "${millisUntilFinished / oneSecondInMilliseconds}"
                    tiempoAMostrar2= segToMin(millisUntilFinished/1000)
                    mBinding.tvP2.text = tiempoAMostrar2
                    bottomTimeLeft--
                }

                // This special function is part of the timer. It runs when the amount of time left
                // in the timer has reached zero and thus the timer is done.
                override fun onFinish() {
                    mBinding.tvP2.setBackgroundColor(Color.RED)

                }
            }

            //update boolean variable
            topClicked = true

            //conditional runs when the top button has been clicked
            if (topClicked) {
                //change colors to show the timer on the bottom clock is running
               mBinding.tvP1.setBackgroundColor(Color.GRAY)
                mBinding.tvP2.setBackgroundColor(Color.TRANSPARENT)

                //disable the top button until the bottom button is clicked
                mBinding.tvP1.isEnabled = false
                mBinding.tvP1.isClickable = false
            }

            //start the timer
            bottomTimer.start()

            //conditional to stop the other timer when this button is clicked
            if (bottomClicked) {
                topTimer.cancel()
                bottomClicked = false
            }

        }


        mBinding.tvP2.setOnClickListener {

            //make sure the top button is enabled
            mBinding.tvP1.isEnabled = true
            mBinding.tvP1.isClickable = true

            mBinding.ibReset.visibility = View.INVISIBLE
            mBinding.ibSettings.visibility = View.INVISIBLE

            //set up timer that will run when the bottom button is clicked
            topTimer = object :
                CountDownTimer(topTimeLeft * oneSecondInMilliseconds, oneSecondInMilliseconds) {
                // This is a special function that is part of the timer. It runs every time the
                // timer's interval passes. In this case, it is set to run every second.
                override fun onTick(millisUntilFinished: Long) {
                    // Update the label to show the remaining time and then remove one second from
                    // the timeLeft variable so that if the timer has to be stopped and started
                    // again, we know how much time to put on the new timer.
                    //mBinding.tvP1.text = "${millisUntilFinished / oneSecondInMilliseconds}"
                    tiempoAMostrar1= segToMin(millisUntilFinished/1000)
                    mBinding.tvP1.text = tiempoAMostrar1
                    topTimeLeft--
                }

                // This special function is part of the timer. It runs when the amount of time left
                // in the timer has reached zero and thus the timer is done.
                override fun onFinish() {
                    mBinding.tvP1.setBackgroundColor(Color.RED)

                }
            }
            //update boolean variable
            bottomClicked = true

            //conditional runs when the bottom button has been clicked
            if (bottomClicked) {
                //change colors to show the timer on the bottom clock is running
                mBinding.tvP1.setBackgroundColor(Color.GRAY)
                mBinding.tvP2.setBackgroundColor(Color.TRANSPARENT)

                //disable the bottom button until the top button is clicked
                mBinding.tvP2.isEnabled = false
                mBinding.tvP2.isClickable = false
            }

            //start the timer
            topTimer.start()

            //conditional to stop the other timer when this button is clicked
            if (topClicked){
                bottomTimer.cancel()
                topClicked = false
            }
        }

        mBinding.ibPausa.setOnClickListener {
            topTimer.cancel()
            bottomTimer.cancel()
            mBinding.ibReset.visibility = View.VISIBLE
            mBinding.ibSettings.visibility = View.VISIBLE

        }


    }

    private fun segToMin(tiempoEnSegundos: Long) : String{
        var min = tiempoEnSegundos / 60

        var seg = tiempoEnSegundos % 60

        return String.format("%02d:%02d",min,seg)

    }

}
