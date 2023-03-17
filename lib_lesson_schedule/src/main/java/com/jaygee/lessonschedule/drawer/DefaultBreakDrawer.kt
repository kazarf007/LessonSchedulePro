package com.jaygee.lessonschedule.drawer

import android.graphics.*
import android.text.TextPaint
import android.util.Log
import com.jaygee.lessonschedule.drawer.base.BreakDrawer
import com.jaygee.lessonschedule.model.BreakLessonCell
import com.jaygee.lessonschedule.util.drawMultiLineText

/**
 *  create on 24/2/2023
 **/
open class DefaultBreakDrawer(var tvSize : Float, val minHeight: Float) : BreakDrawer{

    open val textColor: Int = Color.parseColor("#A29464")

    open val bgColor: Int
        get() = Color.parseColor("#F5F8F4")

    protected val mPaint: TextPaint by lazy {
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
        for (cell in cells) {
//            Log.e("var","第${cell.key.first}节 ， 有 ${cell.value.size} 个")
            for (entry in cell.value) {
//                Log.e("var","第${cell.key.first}节 ， key ${entry.key} 有${entry.value.size}个")
                for (breakLessonCell in entry.value) {
                    Log.e("var","第${cell.key.first}节 ， key ${entry.key}  ， sort ${entry.key} , label ${breakLessonCell.lesson?.label()}")
                }
            }
        }

        for (value in cells.values) {
            inner@ for (vue in value.values) {
                for (breakLessonCell in vue) {
//                    Log.e("var","draw + 1")
                    drawBg(canvas,breakLessonCell.path,mPaint, breakLessonCell)
                    if (breakLessonCell.label.isNotEmpty()){
                        drawContent(canvas ,breakLessonCell ,breakLessonCell.label)
                    }
                }
            }
        }
    }

    open fun drawBg(canvas: Canvas? , path : Path , paint: Paint , cell : BreakLessonCell){
        mPaint.color = bgColor
        canvas?.drawPath(path, mPaint)
    }

    open fun drawContent(canvas: Canvas?, cell: BreakLessonCell, label: List<String?>) {
        mPaint.color = textColor
        mPaint.drawMultiLineText(
            canvas,
            cell.label,
            cell.rectF,
            cell.cellWidth,
            cell.cellHeight,
            drawTextOffsetY,
            textMeasureHeight
        )
    }

}