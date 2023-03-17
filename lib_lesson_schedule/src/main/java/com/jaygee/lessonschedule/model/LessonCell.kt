package com.jaygee.lessonschedule.model

import android.graphics.Path
import android.graphics.RectF

/**
 *  create on 13/2/2023
 **/
open class LessonCell(var rectF: RectF) {

    val cellWidth : Float
        get() = rectF.right - rectF.left

    val cellHeight : Float
        get() = rectF.bottom - rectF.top

    val label = mutableListOf<String>()

    var lesson : Lesson ?= null

    lateinit var path: Path

    init {
        path = Path().apply {
            addRoundRect(rectF, 20f, 20f, Path.Direction.CCW)
        }
    }


}

open class BreakLessonCell(var rectF: RectF) {

    var sort = 0

    var lesson : BreakLesson ?= null

    val label = mutableListOf<String>()

    val cellWidth : Float
        get() = rectF.right - rectF.left

    val cellHeight : Float
        get() = rectF.bottom - rectF.top

    lateinit var path: Path

    init {
        path = Path().apply {
            addRoundRect(rectF, 20f, 20f, Path.Direction.CCW)
        }
    }



}