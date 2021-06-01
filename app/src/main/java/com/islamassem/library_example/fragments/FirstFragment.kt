package com.islamassem.library_example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.islamassem.library_example.R
import com.islamassem.utils.timer.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var timer: TimerCounter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }
    var longTime = 0L
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view.findViewById<Button>(R.id.button_first)
        timer = TimerCounter(TimeType.Second,object : OnTimerTick {
            override fun onTimerTick(timeAsc: Long, timeDesc: Long, timerType: TimeType) {
                btn.post(){
                    btn.text = "${timeAsc} : $timerType"
                }            }
        })
        timer.startTimer()
        btn.setOnLongClickListener {
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            true
        }
        btn.setOnClickListener {
            if(timer.timerStatus == TimerStatus.PAUSED || timer.timerStatus != TimerStatus.STARTED) timer.startTimer() else timer.pause()
        }
    }
}