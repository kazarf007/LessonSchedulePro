package com.jaygee.lessonschedual.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 *  create on 27/2/2023
 **/

private var _drawLength = 0f
private var _drawLines = 0
private var _drawStartY = 0f


fun Paint.drawMultiLineText(
    canvas: Canvas?,
    word: List<String?>,
    rectF: RectF,
    rectWidth: Float,
    rectHeight: Float,
    drawTextOffsetY: Float,
    textMeasureHeight: Float
) {
    if (word.isNotEmpty()) {
        if (word.size == 1) {
            canvas?.drawText(
                word[0] ?: "",
                rectF.left + rectWidth / 2,
                (rectF.top + rectHeight / 2 + drawTextOffsetY),
                this
            )
        } else {
            _drawStartY = (rectHeight - word.size * textMeasureHeight) / 2
            word.forEachIndexed { index, s ->
                canvas?.drawText(
                    s ?: "",
                    rectF.left + rectWidth / 2,
                    (rectF.top + rectHeight / 2 - drawTextOffsetY + index * textMeasureHeight),
                    this
                )
            }
        }
    }
}

private val _sb = StringBuilder()
fun String.generateMultiLineString(paint: Paint, maxWidth: Float): List<String> {
    _sb.setLength(0)
    return if (paint.measureText(this) > maxWidth) {
        this.forEach {
            _sb.append(it)
            _drawLength += paint.measureText(it.toString())
            if (_drawLength > maxWidth) {
                _sb.append("\n")
                _drawLength = 0f
                _drawLines += 1
            }
        }
        _sb.toString().split("\n").filter { it.isNotEmpty() }
    } else {
        listOf(this)
    }
}

fun Paint.drawMultiLineText(
    canvas: Canvas?,
    sb: java.lang.StringBuilder,
    word: String?,
    textDrawPadding: Float,
    rectF: RectF,
    rectWidth: Float,
    rectHeight: Float,
    drawTextOffsetY: Float,
    textMeasureHeight: Float
) {
    sb.setLength(0)
    _drawLines = 0
    if (!word.isNullOrEmpty()) {
        if (this.measureText(word) > rectWidth) {
            val max = rectWidth - textDrawPadding * 2
            word.forEach {
                sb.append(it)
                _drawLength += this.measureText(it.toString())
                if (_drawLength > max) {
                    sb.append("\n")
                    _drawLength = 0f
                    _drawLines += 1
                }
            }
            _drawStartY = (rectHeight - _drawLines * textMeasureHeight) / 2


            sb.toString().split("\n").forEachIndexed { index, s ->
                canvas?.drawText(
                    s,
                    rectF.left + rectWidth / 2,
                    (rectF.top + _drawStartY + drawTextOffsetY + index * textMeasureHeight),
                    this
                )
            }
        } else {
            canvas?.drawText(
                word,
                rectF.left + rectWidth / 2,
                (rectF.top + rectHeight / 2 + drawTextOffsetY),
                this
            )
        }
    }
}