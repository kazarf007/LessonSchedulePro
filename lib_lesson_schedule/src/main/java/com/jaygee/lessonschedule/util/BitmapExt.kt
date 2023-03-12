package com.jaygee.lessonschedule.util

import android.graphics.*
import android.util.Log
import kotlin.math.abs

/**
 *  create on 6/3/2023
 **/

fun generateRoundBitmap(bmp: Bitmap, width: Float, height: Float, rx: Float, ry: Float): Bitmap {
    val origin = generateFitBitmap(bmp, width, height)
    val bitmap = Bitmap.createBitmap(origin.width, origin.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        color = Color.RED
    }
    val disW = (abs(bitmap.width - width) / 2).toInt()
    val disH = (abs(bitmap.height - height) / 2).toInt()
    val rect = Rect(
        disW,
        disH,
        bitmap.width - disW,
        bitmap.height - disH
    )
    val rectF = RectF(0f, 0f, width, height)
    canvas.drawARGB(0, 0, 0, 0)

    canvas.drawRoundRect(rectF, rx, ry, paint)

    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    canvas.drawBitmap(origin, rect, rectF, paint)
    origin.recycle()
    return bitmap
}

fun generateFitBitmap(bmp: Bitmap, width: Float, height: Float): Bitmap {
    return scaleBitmap(bmp, width, height)
}

fun scaleBitmap(bmp: Bitmap, width: Float, height: Float): Bitmap {
    //原始宽高比
    val ratio = 1f * bmp.width / bmp.height
    //目标宽高比
    val ratio2 =  1f * width / height
    var newWidth = 0f
    var newHeight = 0f
    when{
        ratio >= 1 && ratio2 < 1 -> {
            //横图 -> 竖图
            if (bmp.height <= height){
                newWidth = height * ratio
                newHeight = height
            }else{
                newWidth = bmp.width.toFloat()
                newHeight = bmp.height.toFloat()
            }
        }
        ratio < 1 && ratio2 >= 1 -> {
            //竖图 -> 横图
            if (bmp.width <= width){
                newWidth = width
                newHeight = width / ratio
            }else{
                newWidth = bmp.width.toFloat()
                newHeight = bmp.height.toFloat()
            }
        }
        else -> {
            //同类型切换
            newWidth = width
            newHeight = height
        }
    }

    val scaleW = newWidth / bmp.width
    val scaleH = newHeight / bmp.height
    val matrix = Matrix()
    matrix.postScale(scaleW, scaleH)
    val bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height , matrix, false)
    bmp.recycle()
    return bitmap
}