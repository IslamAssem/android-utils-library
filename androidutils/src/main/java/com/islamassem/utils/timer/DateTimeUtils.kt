@file:Suppress("UNUSED_VARIABLE", "unused")

package com.islamassem.utils.timer

import kotlin.math.min

const val SECOND_MILL_SECONDS  =  1000
const val MINUTE_MILL_SECONDS  = 60000
const val HOUR_MILL_SECONDS  = 3600000
const val DAY_MILL_SECONDS  = 86400000
fun convertTime(time:Long,inputType: TimeType,outputType : TimeType = TimeType.Second):Long{
    val milliSeconds = when(inputType){
        TimeType.MilliSecond -> time
        TimeType.Second -> time * SECOND_MILL_SECONDS
        TimeType.Minute -> time * MINUTE_MILL_SECONDS
        TimeType.Hour -> time * HOUR_MILL_SECONDS
        TimeType.Day -> time * DAY_MILL_SECONDS
    }
    return when(outputType){
        TimeType.MilliSecond -> milliSeconds
        TimeType.Second -> if (milliSeconds  % SECOND_MILL_SECONDS >0 ) (1+(milliSeconds * SECOND_MILL_SECONDS))else milliSeconds / SECOND_MILL_SECONDS
        TimeType.Minute -> if (milliSeconds  % MINUTE_MILL_SECONDS >0 ) (1+(milliSeconds / MINUTE_MILL_SECONDS))else milliSeconds / MINUTE_MILL_SECONDS
        TimeType.Hour -> if (milliSeconds  % HOUR_MILL_SECONDS >0 ) (1+(milliSeconds / HOUR_MILL_SECONDS))else milliSeconds / HOUR_MILL_SECONDS
        TimeType.Day -> if (milliSeconds  % DAY_MILL_SECONDS > 0 ) (1+(milliSeconds / DAY_MILL_SECONDS))else milliSeconds / DAY_MILL_SECONDS
    }
}
fun convertTime(time:Int,inputType: TimeType,outputType : TimeType = TimeType.Second):Int{
    val milliSeconds = when(inputType){
        TimeType.MilliSecond -> time
        TimeType.Second -> time * SECOND_MILL_SECONDS
        TimeType.Minute -> time * MINUTE_MILL_SECONDS
        TimeType.Hour -> time * HOUR_MILL_SECONDS
        TimeType.Day -> time * DAY_MILL_SECONDS
    }
    return when(outputType){
        TimeType.MilliSecond -> milliSeconds
        TimeType.Second -> if (milliSeconds  % SECOND_MILL_SECONDS >0 ) (1+(milliSeconds * SECOND_MILL_SECONDS))else milliSeconds / SECOND_MILL_SECONDS
        TimeType.Minute -> if (milliSeconds  % MINUTE_MILL_SECONDS >0 ) (1+(milliSeconds / MINUTE_MILL_SECONDS))else milliSeconds / MINUTE_MILL_SECONDS
        TimeType.Hour -> if (milliSeconds  % HOUR_MILL_SECONDS >0 ) (1+(milliSeconds / HOUR_MILL_SECONDS))else milliSeconds / HOUR_MILL_SECONDS
        TimeType.Day -> if (milliSeconds  % DAY_MILL_SECONDS > 0 ) (1+(milliSeconds / DAY_MILL_SECONDS))else milliSeconds / DAY_MILL_SECONDS
    }
}
fun formatTime(time:Long,timeType: TimeType = TimeType.Second) : String{
    var milliSeconds = convertTime(time,timeType,TimeType.MilliSecond)
    val days = milliSeconds / DAY_MILL_SECONDS
    milliSeconds %= DAY_MILL_SECONDS
    val hours = milliSeconds / HOUR_MILL_SECONDS
    milliSeconds %= HOUR_MILL_SECONDS
    val minutes = milliSeconds / MINUTE_MILL_SECONDS
    milliSeconds %= MINUTE_MILL_SECONDS
    val seconds = convertTime(milliSeconds,TimeType.MilliSecond,TimeType.Second)
    if (days>0)
        return  "%2d:%2d:%2d:%2d".format(days,hours, minutes,seconds)
    if (hours>0)
        return  "%2d:%2d:%2d".format(hours, minutes,seconds)
    return  "%2d:%2d".format(minutes,seconds)
}
fun formatTime(time:Int,timeType: TimeType = TimeType.Second) : String{
    var milliSeconds = convertTime(time,timeType,TimeType.MilliSecond)
    val days = milliSeconds / DAY_MILL_SECONDS
    milliSeconds %= DAY_MILL_SECONDS
    val hours = milliSeconds / HOUR_MILL_SECONDS
    milliSeconds %= HOUR_MILL_SECONDS
    val minutes = milliSeconds / MINUTE_MILL_SECONDS
    milliSeconds %= MINUTE_MILL_SECONDS
    val seconds = convertTime(milliSeconds,TimeType.MilliSecond,TimeType.Second)
    if (days>0)
        return  "%02d:%02d:%02d:%02d".format(days,hours, minutes,seconds)
    if (hours>0)
        return  "%02d:%02d:%02d".format(hours, minutes,seconds)
    return  "%02d:%02d".format(minutes,seconds)
}