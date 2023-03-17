package com.jaygee.lessonschedule.drawer

import android.graphics.Canvas
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
    var marginX: Float,
    var marginY: Float,
    var tvColor: Int,
    var lineColor : Int,
    var bgColor: Int,
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

    protected open val map = mapOf<Int, String>(
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

        mPaint.color = bgColor
        canvas?.drawRect(
            scrollXRatio, scrollYRatio,
            endX + scrollXRatio, marginY + scrollYRatio, mPaint
        )

        mPaint.color = lineColor
        canvas?.drawLine(
            startX + scrollXRatio, marginY + scrollYRatio,
            endX + scrollXRatio, marginY + scrollYRatio, mPaint
        )

        canvas?.drawLine(
            startX + scrollXRatio, height + scrollYRatio,
            endX + scrollXRatio, height + scrollYRatio, mPaint
        )
        mPaint.color = tvColor
        for (entry in axisX) {
            canvas?.drawText(
                map[entry.key ]!!, entry.value + cellWidth / 2,
                scrollYRatio + marginY / 2 + drawTextOffsetY, mPaint
            )
        }
    }

    protected open val lessonTipsMap = mapOf(Pair(1,"第1节09:00-09:45"),Pair(2,"第2节10:00-10:45"),Pair(3,"第3节10:15-11:00"),
        Pair(4,"第4节11:15-12:00"),Pair(5,"第5节14:00-14:45"),Pair(6,"第6节15:00-15:45"),
        Pair(7,"第7节16:15-17:00"),Pair(8,"第8节17:15-18:00"))

    protected open val breakLessonTipsMap = mapOf(Pair(Triple(1,true ,1) , "课前1"),
        Pair(Triple(1,true ,3) , "课前2"),
        Pair(Triple(2,true ,1) , "课前4"),
        Pair(Triple(2,true ,0) , "课前3"),
        Pair(Triple(2,true ,3) , "课前5"),
        Pair(Triple(2,true ,4) , "课前6"),)

    //缓存换行的文本
    private val indexWordMap = mutableMapOf<Int,List<String>>()

    //缓存换行的文本2
    private val indexWordMap2 = mutableMapOf<Triple<Int , Boolean , Int>,List<String>>()

    override fun drawIndex(
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
        scrollY: Int,
    ) {
        val scrollXRatio = scrollX / scale
        val scrollYRatio = scrollY / scale

        mPaint.color = bgColor
        canvas?.drawRect(
            scrollXRatio, scrollYRatio,
            marginX + scrollXRatio, endY + scrollYRatio, mPaint
        )

        mPaint.color = lineColor
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
        mPaint.color = tvColor
        if (lessonTipsMap.isNotEmpty()){
            for (entry in axisY) {
                if (!indexWordMap.containsKey(entry.key)){
                    indexWordMap[entry.key] = lessonTipsMap[entry.key]!!.generateMultiLineString(mPaint , marginX - 10f)
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
        if (breakLessonTipsMap.isNotEmpty()){
            for (entry in breakY) {
                if (!breakLessonTipsMap.containsKey(entry.key)){
                    continue
                }
                if (!indexWordMap2.containsKey(entry.key)){
                    indexWordMap2[entry.key] = breakLessonTipsMap[entry.key]!!.generateMultiLineString(mPaint , marginX - 10f)
                }
                mPaint.drawMultiLineText(
                    canvas, indexWordMap2[entry.key]!!,
                    scrollXRatio,
                    entry.value,
                    marginX,
                    breakCellHeight[entry.key]!!,
                    drawTextOffsetY, textMeasureHeight
                )
            }
        }
    }

    override fun drawXYCrossCover(
        canvas: Canvas?, scale: Float,
        scrollX: Int,
        scrollY: Int,
    ) {
        val scrollXRatio = scrollX / scale
        val scrollYRatio = scrollY / scale

        mPaint.color = bgColor
        canvas?.drawRect(
            scrollXRatio, scrollYRatio,
            marginX + scrollXRatio - 2f, marginY + scrollYRatio - 2f, mPaint
        )
    }
}