package com.jaygee.lessonschedule.drawer

import android.graphics.Paint

/**
 *  create on 1/3/2023
 **/
interface TableDrawer {

    val drawTextOffsetY: Float

    val textMeasureHeight : Float

    fun paint() : Paint
}