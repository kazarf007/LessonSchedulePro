package com.jaygee.lessonschedule.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 *  create on 27/2/2023
 **/

private var _drawLength = 0f
private var _drawStartY = 0f

fun Paint.drawMultiLineText(
    canvas: Canvas?,
    word: List<String?>,
    startX: Float,
    startY: Float,
    maxWidth: Float,
    maxHeight: Float,
    drawTextOffsetY: Float,
    textMeasureHeight: Float
) {
    if (word.isNotEmpty()) {
        if (word.size == 1) {
            canvas?.drawText(
                word[0] ?: "",
                startX + maxWidth / 2,
                (startY + maxHeight / 2 + drawTextOffsetY),
                this
            )
        } else {
            _drawStartY = (maxHeight - word.size * textMeasureHeight) / 2
            val off = drawTextOffsetY * (word.size - 1)
            word.forEachIndexed { index, s ->
                canvas?.drawText(
                    s ?: "",
                    startX + maxWidth / 2,
                    startY + _drawStartY + off + index * textMeasureHeight,
                    this
                )
            }
        }
    }
}

fun Paint.drawMultiLineText(
    canvas: Canvas?,
    word: List<String?>,
    rectF: RectF,
    rectWidth: Float,
    rectHeight: Float,
    drawTextOffsetY: Float,
    textMeasureHeight: Float
) {
    drawMultiLineText(
        canvas,
        word,
        rectF.left,
        rectF.top,
        rectWidth,
        rectHeight,
        drawTextOffsetY,
        textMeasureHeight
    )
}

private val _sb = StringBuilder()
fun String.generateMultiLineString(paint: Paint, maxWidth: Float): List<String> {
    _sb.setLength(0)
    _drawLength = 0f
    val list = mutableListOf<String>()
    val firstSplit = this.split("\n").filter { it.isNotEmpty() }
    firstSplit.forEachIndexed { index, s ->
        if (paint.measureText(s) > maxWidth) {
            s.forEach { char ->
                if (_drawLength + paint.measureText(char.toString()) >= maxWidth) {
                    _sb.append("\n")
                    _sb.append(char)
                    _drawLength = 0f
                } else {
                    _sb.append(char)
                }
                _drawLength += paint.measureText(char.toString())
            }
            list.addAll(_sb.toString().split("\n").filter { it.isNotEmpty() })
        } else {
            list.add(s)
        }
        if (index != firstSplit.lastIndex) {
            _sb.setLength(0)
            _drawLength = 0f
        }
    }

    return list
}
