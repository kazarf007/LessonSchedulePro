package com.jaygee.lessonschedule.util

/**
 *  create on 2/3/2023
 **/
interface ScheduleClickListener {

    fun clickLesson(weekNo : Int , lessonIndexNo : Int)

    fun clickBreakLesson(weekNo: Int , lessonIndexNo : Int , isBefore : Boolean , sort : Int)
}