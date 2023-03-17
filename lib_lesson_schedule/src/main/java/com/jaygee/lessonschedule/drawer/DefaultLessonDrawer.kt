package com.jaygee.lessonschedule.drawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import com.jaygee.lessonschedule.drawer.base.LessonDrawer
import com.jaygee.lessonschedule.model.LessonCell
import com.jaygee.lessonschedule.util.drawMultiLineText


/**
 *  create on 13/2/2023
 **/
open class DefaultLessonDrawer(var tvSize: Float) : LessonDrawer {

    open val textColor: Int = Color.parseColor("#000000")

    open val bgColor: Int
        get() = Color.parseColor("#F0F2F7")

    open val emptyColor: Int
        get() = Color.parseColor("#F0F2F7")


    protected val mPaint: TextPaint by lazy {
        TextPaint().apply {
            style = Paint.Style.FILL_AND_STROKE
            this.textSize = tvSize
            this.isDither = true
            this.isAntiAlias = true
            this.textAlign = Paint.Align.CENTER
        }
    }

    override fun paint() = mPaint

    override val drawTextOffsetY: Float
        get() = (mPaint.fontMetrics.descent - mPaint.fontMetrics.ascent) / 2 - mPaint.fontMetrics.descent

    override val textMeasureHeight: Float
        get() = mPaint.fontMetrics.descent - mPaint.fontMetrics.ascent


    override fun draw(canvas: Canvas?, cell: LessonCell, label: List<String?>) {
        drawBg(canvas, cell, label, mPaint)
        drawContent(canvas, cell, label)
    }

    open fun drawBg(
        canvas: Canvas?,
        cell: LessonCell,
        label: List<String?>,
        paint: Paint
    ) {
        mPaint.color = if (label.isEmpty() || label.none { !it.isNullOrEmpty() }) {
            emptyColor
        } else {
            bgColor
        }
        canvas?.drawPath(cell.path, mPaint)
    }

    open fun drawContent(canvas: Canvas?, cell: LessonCell, label: List<String?>) {
        mPaint.color = textColor
        mPaint.drawMultiLineText(
            canvas,
            label,
            cell.rectF,
            cell.cellWidth,
            cell.cellHeight,
            drawTextOffsetY,
            textMeasureHeight
        )
    }
}