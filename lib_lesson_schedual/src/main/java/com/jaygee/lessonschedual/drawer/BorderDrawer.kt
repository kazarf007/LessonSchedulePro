package com.jaygee.lessonschedual.drawer

import android.graphics.Canvas

/**
 *  create on 24/2/2023
 **/
interface BorderDrawer : TableDrawer {

    /**
     * 左边距 - 一般用来放左侧序号
     */
    fun xBorderSize() : Float

    /**
     * 左边距 - 一般用来放左侧序号
     */
    fun yBorderSize() : Float

    /**
     * 边界线的宽度
     */
    fun lineSize(): Float

    fun lessonIndexMargin(): Float

    fun drawWeek(
        canvas: Canvas?,
        startX: Float,
        endX: Float,
        height: Float,
        cellWidth: Int,
        axisX: Map<Int, Float>,
        scale: Float,
        scrollX: Int,
        scrollY: Int
    )

    /**
     * 绘制y轴及相关
     * @param startY 起点y
     * @param endY 终点y
     */
    fun drawIndex(
        canvas: Canvas?,
        startY: Float,
        endY: Float,
        width: Float,
        cellHeight: Float,
        axisY: Map<Int, Float>,
        scale: Float,
        scrollX: Int,
        scrollY: Int
    )

    fun drawXYCrossCover(
        canvas: Canvas?, scale: Float,
        scrollX: Int,
        scrollY: Int,
    )
}