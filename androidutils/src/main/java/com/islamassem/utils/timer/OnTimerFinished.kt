package com.islamassem.utils.timer

fun interface OnTimerFinished {
    fun onTimerFinished(time:Long,timerType: TimeType)
}