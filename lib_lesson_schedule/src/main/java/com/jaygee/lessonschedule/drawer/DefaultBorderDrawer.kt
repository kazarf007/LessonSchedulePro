package com.jaygee.lessonschedule.drawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import com.jaygee.lessonschedule.drawer.base.BorderDrawer
import com.jaygee.lessonschedule.util.drawMultiLineText
import com.jaygee.lessonschedule.util.generateMultiLineString

/**
 *  create on 24/2/2023
 **/
open class DefaultBorderDrawer(
    var tvSize: Float,
    var color: Int,
    var marginX: Float,
    var marginY: Float
) : BorderDrawer {

    protected val mPaint: TextPaint by lazy {
        TextPaint().apply {
            style = Paint.Style.FILL_AND_STROKE
            this.textSize = tvSize
            this.isDither = true
            this.isAntiAlias = true
            this.color = color
            this.strokeWidth = 2f
            this.textAlign = Paint.Align.CENTER
            this.strokeJoin = Paint.Join.ROUND
            this.strokeCap = Paint.Cap.ROUND
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

    open val map = mapOf<Int, String>(
        Pair(1, "MON"),
        Pair(2, "TUE"),
        Pair(3, "WEN"),
        Pair(4, "THR"),
        Pair(5, "FRI"),
        Pair(6, "SAT"),
        Pair(7, "SUN")
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

    private val mp = mapOf(Pair(1,"???1???09:00-09:45"),Pair(2,"???2???10:00-10:45"),Pair(3,"???3???10:15-11:00"),
        Pair(4,"???4???11:15-12:00"),Pair(5,"???5???14:00-14:45"),Pair(6,"???6???15:00-15:45"),
        Pair(7,"???7???16:15-17:00"),Pair(8,"???8???17:15-18:00"))

    //?????????????????????
    private val indexWordMap = mutableMapOf<Int,List<String>>()

    override fun drawIndex(
        canvas: Canvas?,
        startY: Float,
        endY: Float,
        width: Float,
        cellHeight: Float,
        textPaddingSize : Float,
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
            if (!indexWordMap.containsKey(entry.key)){
                indexWordMap[entry.key] = mp[entry.key]!!.generateMultiLineString(mPaint , marginX - 10f)
            }
            mPaint.drawMultiLineText(
                canvas, indexWordMap[entry.key]!!,
                scrollXRatio,
                entry.value,
                marginX,
                cellHeight,
                drawTextOffsetY, textMeasureHeight
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