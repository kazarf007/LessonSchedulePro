package com.jaygee.lessonschedule.drawer.base

import android.graphics.Canvas
import com.jaygee.lessonschedule.drawer.base.TableDrawer
import com.jaygee.lessonschedule.model.BreakLessonCell

/**
 *  create on 24/2/2023
 **/
interface BreakDrawer : TableDrawer {

    /**
     * @param cells key <第？节课 ， 课前/课后> /  value < sort , 相同sort的课程 >
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