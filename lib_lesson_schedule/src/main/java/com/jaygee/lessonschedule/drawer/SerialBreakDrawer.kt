package com.jaygee.lessonschedule.drawer

import android.graphics.*
import android.text.TextPaint
import com.jaygee.lessonschedule.model.BreakLessonCell
import com.jaygee.lessonschedule.util.drawMultiLineText

/**
 *  create on 24/2/2023
 **/
class SerialBreakDrawer(val textSize: Float, val minHeight: Float) : BreakDrawer {

    private val mPaint: TextPaint by lazy {
        TextPaint().apply {
            style = Paint.Style.FILL_AND_STROKE
            this.textSize = this@SerialBreakDrawer.textSize
            this.isDither = true
            this.isAntiAlias = true
            this.strokeWidth = 1f
            this.textAlign = Paint.Align.CENTER
        }
    }

    override fun minHeight() = minHeight

    override fun paint() = mPaint

    private var heightSize = 100f

    override val drawTextOffsetY: Float
        get() = (mPaint.fontMetrics.descent - mPaint.fontMetrics.ascent) / 2 - mPaint.fontMetrics.descent

    override val textMeasureHeight: Float
        get() = mPaint.fontMetrics.descent - mPaint.fontMetrics.ascent

    override fun drawBreak(
        canvas: Canvas?,
        cells : Map<Pair<Int, Boolean>, Map<Int , List<BreakLessonCell>>>
    ) {
        for (value in cells.values) {
            for (vue in value.values) {
                for (breakLessonCell in vue) {
                    mPaint.color = Color.LTGRAY
                    canvas?.drawPath(breakLessonCell.path , mPaint)
                    mPaint.color = Color.BLACK
                    mPaint.drawMultiLineText(
                        canvas, breakLessonCell.label,
                        breakLessonCell.rectF, breakLessonCell.cellWidth, breakLessonCell.cellHeight, drawTextOffsetY, textMeasureHeight
                    )
                }
            }
        }
    }

    override fun breakHeight() = heightSize

    override fun configMaxLine(num: Int) {
        heightSize = minHeight * (num + 1)
    }
}