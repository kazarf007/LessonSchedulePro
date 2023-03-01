package com.jaygee.lessonschedual.drawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint

/**
 *  create on 24/2/2023
 **/
class DefaultBorderDrawer(
    var tvSize: Float,
    var color: Int,
    var marginX: Float,
    var marginY: Float
) : BorderDrawer {

    private val mPaint: TextPaint by lazy {
        TextPaint().apply {
            style = Paint.Style.FILL_AND_STROKE
            this.textSize = tvSize
            this.isDither = true
            this.isAntiAlias = true
            this.color = color
            this.strokeWidth = 2f
            this.textAlign = Paint.Align.CENTER
        }
    }

    override fun paint() = mPaint

    override val drawTextOffsetY: Float
        get() = (mPaint.fontMetrics.descent - mPaint.fontMetrics.ascent) / 2 - mPaint.fontMetrics.descent

    override val textMeasureHeight: Float
        get() = mPaint.fontMetrics.descent - mPaint.fontMetrics.ascent

    override fun xBorderSize() = marginX

    override fun yBorderSize() = marginY


    override fun lineSize() = 2f

    override fun lessonIndexMargin() = marginY + lineSize() * 2

    val map = mapOf<Int, String>(
        Pair(1, "周一"),
        Pair(2, "周二"),
        Pair(3, "周三"),
        Pair(4, "周四"),
        Pair(5, "周五"),
        Pair(6, "周六"),
        Pair(7, "周日")
    )


    override fun drawWeek(
        canvas: Canvas?,
        startX: Float,
        endX: Float,
        height: Float,
        cellWidth: Int,
        axisX: Map<Int, Float>,
        scale: Float,
        scrollX: Int,
        scrollY: Int
    ) {
        val scrollXRatio = scrollX / scale
        val scrollYRatio = scrollY / scale

        mPaint.color = Color.WHITE
        canvas?.drawRect(
            scrollXRatio, scrollYRatio,
            endX + scrollXRatio, marginY + scrollYRatio, mPaint
        )

        mPaint.color = color
        canvas?.drawLine(
            startX + scrollXRatio, marginY + scrollYRatio,
            endX + scrollXRatio, marginY + scrollYRatio, mPaint
        )

        canvas?.drawLine(
            startX + scrollXRatio, height + scrollYRatio,
            endX + scrollXRatio, height + scrollYRatio, mPaint
        )

        for (entry in axisX) {
            canvas?.drawText(
                map[entry.key ]!!, entry.value + cellWidth / 2,
                scrollYRatio + marginY / 2 + drawTextOffsetY, mPaint
            )
        }
    }

    private val indexWordMap = mutableMapOf<String,List<String>>()

    override fun drawIndex(
        canvas: Canvas?,
        startY: Float,
        endY: Float,
        width: Float,
        cellHeight: Float,
        axisY: Map<Int, Float>,
        scale: Float,
        scrollX: Int,
        scrollY: Int,
    ) {
        val scrollXRatio = scrollX / scale
        val scrollYRatio = scrollY / scale

        mPaint.color = Color.WHITE
        canvas?.drawRect(
            scrollXRatio, scrollYRatio,
            marginX + scrollXRatio, endY + scrollYRatio, mPaint
        )

        mPaint.color = color
        canvas?.drawLine(
            marginX + scrollXRatio,
            startY + scrollYRatio,
            marginX + scrollXRatio,
            endY + scrollYRatio,
            mPaint
        )
        canvas?.drawLine(
            width + scrollXRatio,
            startY + scrollYRatio,
            width + scrollXRatio,
            endY + scrollYRatio,
            mPaint
        )
        for (entry in axisY) {
            canvas?.drawText(
                "第${entry.key}节",
                scrollXRatio + marginX / 2,
                entry.value + cellHeight / 2 + drawTextOffsetY, mPaint
            )
        }
    }

    override fun drawXYCrossCover(
        canvas: Canvas?, scale: Float,
        scrollX: Int,
        scrollY: Int,
    ) {
        val scrollXRatio = scrollX / scale
        val scrollYRatio = scrollY / scale

        mPaint.color = Color.WHITE
        canvas?.drawRect(
            scrollXRatio, scrollYRatio,
            marginX + scrollXRatio - 2f, marginY + scrollYRatio - 2f, mPaint
        )
    }
}