package com.islamassem.utils.timer

import java.lang.Exception
import java.util.*
import kotlin.concurrent.fixedRateTimer
@Suppress("unused", "MemberVisibilityCanBePrivate")
class TimerCounter(timeType: TimeType,onTimerTick: OnTimerTick) {
    var onTimerTick : OnTimerTick
    var onTimerFinished : OnTimerFinished? = null
    private var timer : Timer? = null
    var timerStatus = TimerStatus.IDLE
    private var tickTime : Long = 0
    private var time : Long = 0
    private var timeInSeconds : Long = 0
     var timeType : TimeType
    var passedTime : Long = 0
        private set

    constructor(
        onTimerTick: OnTimerTick,
        onTimerFinished: OnTimerFinished,
        time: Long,
        timeType: TimeType) :this(timeType,onTimerTick){
        this.onTimerTick = onTimerTick
        this.onTimerFinished = onTimerFinished
        this.time = time
        timeInSeconds = convertTime(time,timeType,TimeType.Second)
        this.timeType = timeType
        tickTime = when(timeType){
            TimeType.MilliSecond -> SECOND_MILL_SECONDS.toLong()
            TimeType.Second -> SECOND_MILL_SECONDS.toLong()
            TimeType.Minute -> MINUTE_MILL_SECONDS.toLong()
            TimeType.Hour -> HOUR_MILL_SECONDS.toLong()
            TimeType.Day -> DAY_MILL_SECONDS.toLong()
        }
    }

    init {
        this.onTimerTick = onTimerTick
        this.timeType = timeType
        tickTime = when(timeType){
            TimeType.MilliSecond -> SECOND_MILL_SECONDS.toLong()
            TimeType.Second -> SECOND_MILL_SECONDS.toLong()
            TimeType.Minute -> MINUTE_MILL_SECONDS.toLong()
            TimeType.Hour -> HOUR_MILL_SECONDS.toLong()
            TimeType.Day -> DAY_MILL_SECONDS.toLong()
        }
    }
    fun startTimer(){

        if (timerStatus != TimerStatus.PAUSED)
        {  passedTime = 0L
            cancel()
        }
        if (time > 0L)
            startRegularTimer()
        else startInfiniteTimer()
    }
    private fun startRegularTimer(){
        timerStatus = TimerStatus.STARTED
        timer = fixedRateTimer(initialDelay = 0L,period = tickTime) {
            if (timerStatus == TimerStatus.STARTED){
                if (passedTime >= time){
                    onTimerFinished?.onTimerFinished(time,timeType)
                    this@TimerCounter.finishTimer(this)
                }
                else onTimerTick.onTimerTick((++passedTime),passedTime,timeType)

            }
        }
    }
    private fun startInfiniteTimer(){
        onTimerTick.let {
            timerStatus = TimerStatus.STARTED
            timer = fixedRateTimer(initialDelay = 0L,period = tickTime) {
                if (timerStatus == TimerStatus.STARTED)
                    onTimerTick.onTimerTick((++passedTime),passedTime,timeType)
            }
        }
    }
    private fun finishTimer(timerTask : TimerTask){
        try {
            timerTask.cancel()
        }catch (ignored : Exception){}
        try {
            timer?.cancel()
        }catch (ignored : Exception){}
        timerStatus = TimerStatus.FINISHED
    }
    fun cancel(){
        timerStatus = TimerStatus.IDLE
        timer = try {
            timer?.cancel()
            null
        }catch (e : Exception){
            null
        }
    }
    fun pause(){
        timerStatus = TimerStatus.PAUSED
        timer = try {
            timer?.cancel()
            null
        }catch (e : Exception){
            null
        }
    }

}