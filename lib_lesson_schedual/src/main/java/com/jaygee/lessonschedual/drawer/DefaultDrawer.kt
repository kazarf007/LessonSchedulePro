package com.jaygee.lessonschedual.drawer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import com.jaygee.lessonschedual.model.LessonCell
import com.jaygee.lessonschedual.util.drawMultiLineText


/**
 *  create on 13/2/2023
 **/
class DefaultDrawer(textSize: Float, var textColor: Int, var bgColor: Int) : LessonDrawer {

    val emptyColor: Int by lazy {
        Color.parseColor("#F0F2F7")
    }

    val mPaint: TextPaint by lazy {
        TextPaint().apply {
            style = Paint.Style.FILL_AND_STROKE
            this.textSize = textSize
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


    override fun draw(canvas: Canvas?, cell: LessonCell, label : List<String?>) {
        mPaint.color = if (label.isEmpty() || label.none { !it.isNullOrEmpty() }) {
            emptyColor
        } else {
            bgColor
        }
        canvas?.drawPath(cell.path, mPaint)
        mPaint.color = textColor
        mPaint.drawMultiLineText(
            canvas, label, cell.rectF, cell.cellWidth , cell.cellHeight, drawTextOffsetY, textMeasureHeight
        )
    }

}