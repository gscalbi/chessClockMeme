package com.example.chessclockmeme

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.chessclockmeme.databinding.FragmentRelojBinding
var topInitialTime: Long = 35
var topTimeLeft = topInitialTime
var bottomInitialTime: Long = 35
var bottomTimeLeft = bottomInitialTime
const val oneSecondInMilliseconds: Long =  1000
var topClicked = false
var bottomClicked= false
var tiempoAMostrar1 = "00:00"
var tiempoAMostrar2= "00:00"


class RelojFragment : Fragment() {

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


    private lateinit var mBinding: FragmentRelojBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= FragmentRelojBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //variables que contienen las canciones
        val mediaDiez = MediaPlayer.create(context,R.raw.diezseg)
        val mediaTreinta = MediaPlayer.create(context,R.raw.treintaseg)


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

                    if (bottomTimeLeft<30){
                        if (bottomTimeLeft<10){
                            mediaTreinta.stop()
                            mediaDiez.start()
                        }else  {
                            mediaTreinta.start()
                        }

                    }

                }

                // This special function is part of the timer. It runs when the amount of time left
                // in the timer has reached zero and thus the timer is done.
                override fun onFinish() {
                    mBinding.tvP2.setBackgroundColor(Color.RED)
                    mediaDiez.stop()

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

                    if (topTimeLeft<30){
                        if (topTimeLeft<10){
                            mediaTreinta.stop()
                            mediaDiez.start()

                        }else  {
                            mediaTreinta.start()
                        }

                    }
                }

                // This special function is part of the timer. It runs when the amount of time left
                // in the timer has reached zero and thus the timer is done.
                override fun onFinish() {
                    mBinding.tvP1.setBackgroundColor(Color.RED)
                    mediaDiez.stop()

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

        mBinding.ibReset.setOnClickListener {
            topTimeLeft = topInitialTime
            bottomTimeLeft= bottomInitialTime
            tiempoAMostrar1 = segToMin(topInitialTime)
            tiempoAMostrar2 = segToMin(bottomInitialTime)
            mBinding.tvP1.text= tiempoAMostrar1
            mBinding.tvP2.text = tiempoAMostrar2
        }
        mBinding.ibSettings.setOnClickListener {
            lanzarAjustesFragment()
        }

    }


    private fun segToMin(tiempoEnSegundos: Long) : String{
        var min = tiempoEnSegundos / 60

        var seg = tiempoEnSegundos % 60

        return String.format("%02d:%02d",min,seg)

    }

    private fun lanzarAjustesFragment(args:Bundle? = null) {
        val fragment = AjustesFragment()
        if(args!=null) fragment.arguments= args

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.hostFragment,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
    }



}