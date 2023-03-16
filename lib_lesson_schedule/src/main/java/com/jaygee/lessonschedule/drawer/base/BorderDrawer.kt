package com.jaygee.lessonschedule.drawer.base

import android.graphics.Canvas
import com.jaygee.lessonschedule.drawer.base.TableDrawer

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
     * @param width y轴宽度
     * @param cellHeight 正课的高度
     * @param breakCellHeight 课间的高度
     * @param textPaddingSize 文字的左右间距
     * @param axisY 正课文字的y坐标
     * @param breakY 课间文字的y坐标
     * @param scale 缩放
     * @param scrollX 滑动因子
     * @param scrollY 滑动因子
     */
    fun drawIndex(
        canvas: Canvas?,
        startY: Float,
        endY: Float,
        width: Float,
        cellHeight: Float,
        breakCellHeight : Map<Triple<Int , Boolean , Int>, Float>,
        textPaddingSize : Float,
        axisY: Map<Int, Float>,
        breakY : Map<Triple<Int , Boolean , Int>, Float>,
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