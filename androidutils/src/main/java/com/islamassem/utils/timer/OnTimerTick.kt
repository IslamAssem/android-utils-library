package com.islamassem.utils.timer

fun interface OnTimerTick {
    fun onTimerTick(timeAsc : Long,timeDesc : Long,timerType: TimeType)
}