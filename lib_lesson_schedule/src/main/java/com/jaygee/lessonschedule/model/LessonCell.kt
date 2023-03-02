package com.jaygee.lessonschedule.model

import android.graphics.Path
import android.graphics.RectF

/**
 *  create on 13/2/2023
 **/
open class LessonCell(var rectF: RectF) {

    lateinit var path: Path

    init {
        path = Path().apply {
            addRoundRect(rectF, 10f, 10f, Path.Direction.CCW)
        }
    }

    val cellWidth : Float
        get() = rectF.right - rectF.left

    val cellHeight : Float
        get() = rectF.bottom - rectF.top

    val label = mutableListOf<String>()
}

open class BreakLessonCell(var rectF: RectF) {

    val label = mutableListOf<String>()

    lateinit var path: Path

    init {
        path = Path().apply {
            addRoundRect(rectF, 10f, 10f, Path.Direction.CCW)
        }
    }

    val cellWidth : Float
        get() = rectF.right - rectF.left

    val cellHeight : Float
        get() = rectF.bottom - rectF.top

}