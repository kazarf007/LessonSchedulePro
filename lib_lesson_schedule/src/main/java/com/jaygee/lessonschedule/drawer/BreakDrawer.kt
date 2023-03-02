package com.jaygee.lessonschedule.drawer

import android.graphics.Canvas
import com.jaygee.lessonschedule.model.BreakLessonCell

/**
 *  create on 24/2/2023
 **/
interface BreakDrawer : TableDrawer {

    /**
     * @param bl key 第几节课 value : 课间数组 最大size = 2 ，课前 ，课后
     */
    fun drawBreak(
        canvas: Canvas?,
        cells: Map<Pair<Int, Boolean>, Map<Int, List<BreakLessonCell>>>
    )

    /**
     * 最小高度
     */
    fun minHeight () : Float
}