package com.jaygee.lessonschedual.drawer

import android.graphics.Canvas
import com.jaygee.lessonschedual.model.LessonCell

/**
 *  create on 13/2/2023
 **/
interface LessonDrawer : TableDrawer {

    fun draw(canvas: Canvas? , cell : LessonCell, label : List<String?>)

}