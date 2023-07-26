package com.quizApp.ui.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quizApp.databinding.FragmentTrainingBinding


class TrainingFragment : Fragment() {

    private lateinit var timerRun: CountDownTimer
    private lateinit var timerJump: CountDownTimer
    private lateinit var timerBike: CountDownTimer
    private lateinit var timerAerobic: CountDownTimer
    private var isTimerRunning = false
    private var remainingTimeInMillis: Long = 0

    private val timerDurationRun: Long = 3_600_000
    private val timerDurationBike: Long = 3_600_000
    private val timerDurationJump: Long = 900_000
    private val timerDurationAerobic: Long = 2_700_700

    private lateinit var binding: FragmentTrainingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(layoutInflater, container, false)

        timerRun = object : CountDownTimer(timerDurationRun, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                binding.textMinRun.text = (remainingTimeInMillis / 1000 / 60).toString()
                binding.textSecRun.text = (remainingTimeInMillis / 1000 % 60).toString()
            }

            override fun onFinish() {
                isTimerRunning = false
            }
        }
        timerJump = object : CountDownTimer(timerDurationJump, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                binding.textMinJump.text = (remainingTimeInMillis / 1000 / 60).toString()
                binding.textSecJump.text = (remainingTimeInMillis / 1000 % 60).toString()
            }

            override fun onFinish() {
                isTimerRunning = false
            }
        }
        timerBike = object : CountDownTimer(timerDurationBike, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                binding.textMinBike.text = (remainingTimeInMillis / 1000 / 60).toString()
                binding.textSecBike.text = (remainingTimeInMillis / 1000 % 60).toString()
            }

            override fun onFinish() {
                isTimerRunning = false
            }
        }
        timerAerobic = object : CountDownTimer(timerDurationAerobic, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                binding.textMinAerobic.text = (remainingTimeInMillis / 1000 / 60).toString()
                binding.textSecAerobic.text = (remainingTimeInMillis / 1000 % 60).toString()
            }

            override fun onFinish() {
                isTimerRunning = false
            }
        }

        with(binding) {
            btStartRun.setOnClickListener {
                timerRun.start()
                timerJump.cancel()
                timerBike.cancel()
                timerAerobic.cancel()
            }

            btStartJump.setOnClickListener {
                timerRun.cancel()
                timerJump.start()
                timerBike.cancel()
                timerAerobic.cancel()
            }

            btStartBike.setOnClickListener {
                timerRun.cancel()
                timerJump.cancel()
                timerBike.start()
                timerAerobic.cancel()
            }

            btStartAerobic.setOnClickListener {
                timerRun.cancel()
                timerJump.cancel()
                timerBike.cancel()
                timerAerobic.start()
            }







            resetAll.setOnClickListener {
                timerRun.cancel()
                timerJump.cancel()
                timerBike.cancel()
                timerAerobic.cancel()
                resetViews()
            }
        }



        return binding.root
    }

    private fun resetViews() {
        with(binding) {
            textMinRun.text = "60"
            textSecRun.text = "00"

            textMinJump.text = "15"
            textSecJump.text = "00"

            textMinBike.text = "60"
            textSecBike.text = "00"

            textMinAerobic.text = "45"
            textSecAerobic.text = "00"

        }
    }


}