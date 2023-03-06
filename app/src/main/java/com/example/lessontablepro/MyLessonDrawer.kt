package com.example.lessontablepro

import android.content.Context
import android.graphics.*
import android.util.Log
import com.jaygee.lessonschedule.drawer.DefaultLessonDrawer
import com.jaygee.lessonschedule.drawer.SerialBreakDrawer
import com.jaygee.lessonschedule.model.LessonCell
import com.jaygee.lessonschedule.util.generateRoundBitmap

/**
 *  create on 1/3/2023
 **/
class MyLessonDrawer(val context: Context) : DefaultLessonDrawer(54f) {

    override val textColor
        get() = Color.parseColor("#64A291")

    override val bgColor: Int
        get() = Color.parseColor("#EAF8EF")

    override val emptyColor: Int
        get() = Color.parseColor("#F0F2F7")

    lateinit var imgMath : Bitmap


    override fun drawBg(canvas: Canvas?, cell: LessonCell, label: List<String?>, paint: Paint) {
        if (label.any { it?.contains("math") == true }){
//            canvas?.drawBitmap(imgMath, rect, cell.rectF ,paint)

            if (!::imgMath.isInitialized){
                imgMath = generateRoundBitmap(BitmapFactory.decodeResource(context.resources , R.mipmap.img_math) ,
                    cell.cellWidth , cell.cellHeight,20f, 20f)
            }
//            canvas?.drawBitmap(imgMath, rect ,cell.rectF, null)
            canvas?.drawBitmap(imgMath, cell.rectF.left , cell.rectF.top, null)
        }else{
            super.drawBg(canvas, cell, label, paint)
        }
    }

    override fun drawContent(canvas: Canvas?, cell: LessonCell, label: List<String?>) {
        if (label.none { it?.contains("math") == true }){
            super.drawContent(canvas, cell, label)
        }
    }
}


class MyBreakDrawer : SerialBreakDrawer(54f ,10f){

    override val textColor: Int
        get() = Color.parseColor("#A29464")

    override val bgColor: Int
        get() = Color.parseColor("#F5F8F4")

    val borderColor = Color.parseColor("#CEDCC7")

    val dashPathEffect = DashPathEffect(floatArrayOf(4f,4f) ,0f)

    override fun drawBg(canvas: Canvas?, path: Path, paint: Paint) {
        paint.color = bgColor
        paint.style = Paint.Style.FILL
        canvas?.drawPath(path , paint)
        paint.style = Paint.Style.STROKE
        paint.color = borderColor
        paint.strokeWidth = 4f
        paint.pathEffect = dashPathEffect
        canvas?.drawPath(path , paint)
        paint.style = Paint.Style.FILL
    }
}