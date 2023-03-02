package com.example.lessontablepro

import com.jaygee.lessonschedule.model.BreakLesson
import com.jaygee.lessonschedule.model.Lesson
import com.jaygee.lessonschedule.model.SerialBreakLesson

/**
 *  create on 1/3/2023
 **/


class InnerLesson(val x : Int ,val y : Int , val label : String) : Lesson {
    override fun weekNo() = x

    override fun lessonIndex() = y

    override fun label() = label
}

class InnerBreakLesson(val y : Int , val label : String , val isBefore : Boolean, val sort : Int) :
    SerialBreakLesson {

    override fun lessonIndex() = y

    override fun label() = label

    override fun isBeforeLesson() = isBefore

    override fun sort() = sort
}

class Inner2BreakLesson(val x : Int , var y : Int , val label : String , val isBefore : Boolean , val sort : Int) :
    BreakLesson {
    override fun weekNo() = x

    override fun lessonIndex() = y

    override fun label() = label

    override fun isBeforeLesson() = isBefore

    override fun sort() = sort
}