package com.jaygee.lessonschedule.drawer

import android.graphics.*
import android.text.TextPaint
import com.jaygee.lessonschedule.drawer.base.BreakDrawer
import com.jaygee.lessonschedule.model.BreakLessonCell
import com.jaygee.lessonschedule.util.drawMultiLineText

/**
 *  create on 24/2/2023
 **/
open class SerialBreakDrawer(var tvSize : Float , val minHeight: Float) : BreakDrawer {

    open val textColor: Int = Color.parseColor("#A29464")

    open val bgColor: Int
        get() = Color.parseColor("#F5F8F4")

    private val mPaint: TextPaint by lazy {
        TextPaint().apply {
            style = Paint.Style.FILL_AND_STROKE
            this.textSize = tvSize
            this.isDither = true
            this.isAntiAlias = true
            this.strokeWidth = 1f
            this.textAlign = Paint.Align.CENTER
        }
    }

    override fun minHeight() = minHeight + textMeasureHeight * 2

    override fun paint() = mPaint

    override val drawTextOffsetY: Float
        get() = (mPaint.fontMetrics.descent - mPaint.fontMetrics.ascent) / 2 - mPaint.fontMetrics.descent

    override val textMeasureHeight: Float
        get() = mPaint.fontMetrics.descent - mPaint.fontMetrics.ascent

    override fun drawBreak(
        canvas: Canvas?,
        cells: Map<Pair<Int, Boolean>, Map<Int, List<BreakLessonCell>>>
    ) {
        for (value in cells.values) {
            for (vue in value.values) {
                for (breakLessonCell in vue) {
                    drawBg(canvas,breakLessonCell.path,mPaint , breakLessonCell)
                    mPaint.color = textColor
                    mPaint.drawMultiLineText(
                        canvas,
                        breakLessonCell.label,
                        breakLessonCell.rectF,
                        breakLessonCell.cellWidth,
                        breakLessonCell.cellHeight,
                        drawTextOffsetY,
                        textMeasureHeight
                    )
                }
            }
        }
    }

    open fun drawBg(canvas: Canvas? , path : Path , paint: Paint , cell : BreakLessonCell){
        mPaint.color = bgColor
        canvas?.drawPath(path, mPaint)
    }

}