package com.jaygee.lessonschedual.util

import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region

/**
 *  create on 24/2/2023
 **/

object ClickJudger {

    private var pressDownTime = 0L

    private var moveX = 0f

    private var moveY = 0f

    fun recordPressDownTime(){
        pressDownTime = System.currentTimeMillis()
    }

    fun recordMove(x : Float , y : Float){
        moveX = x
        moveY = y
    }

    fun isClickEvent() : Boolean{
        return (System.currentTimeMillis() - pressDownTime < 200L ) && ( moveX <= 30f && moveY <= 30f )
    }

    fun isClickPath(path: Path, x: Float, y: Float): Boolean {
        val rect = RectF()
        path.computeBounds(rect, true)
        val region = Region()
        region.setPath(
            path,
            Region(rect.left.toInt(), rect.top.toInt(), rect.right.toInt(), rect.bottom.toInt())
        )
        return region.contains(x.toInt(), y.toInt())
    }
}

