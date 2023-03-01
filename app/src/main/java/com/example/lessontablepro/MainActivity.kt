package com.example.lessontablepro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jaygee.lessonschedual.LessonSchedualProView
import com.jaygee.lessonschedual.model.BreakLesson
import com.jaygee.lessonschedual.model.Lesson
import com.jaygee.lessonschedual.model.SerialBreakLesson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val table = findViewById<LessonSchedualProView>(R.id.table)
        table.setLessonData(listOf(
            InnerLesson(1,1,"英语-222-李菲菲"),
            InnerLesson(2,2,"数学-123-李丝丝"),
            InnerLesson(3,3,"政治-546-李芝芝"),
            InnerLesson(4,4,"英语-多媒体教室302-kazarf"),
            InnerLesson(5,5,"地理-222-李丽丽"),
            InnerLesson(6,5,"outer!!!"),
            InnerLesson(4,6,"物理-214-李莉莉"),
            InnerLesson(3,7,"你的"),
            InnerLesson(2,8,"asdasdas"),
        )).setSerialBreakLessonData(listOf(
            InnerBreakLesson(1 , "晨跑" , true , 0),
            Inner2BreakLesson(1,1 , "语文早读" , true , 2),
            Inner2BreakLesson(2,1 , "英语早读" , true, 2),
            Inner2BreakLesson(3,1 , "政治早读" , true, 2),
            Inner2BreakLesson(4,1 , "地理早读" , true, 2),
            Inner2BreakLesson(5,1 , "生物早读" , true, 2),
            Inner2BreakLesson(6,1 , "生物sxxx早读" , true, 2),
            Inner2BreakLesson(1,2 , "升旗仪式" , false, 3),
            InnerBreakLesson(1 , "早餐时间" , true , 3),
            InnerBreakLesson(2 , "课间操" , false, 0),
            InnerBreakLesson(6 , "眼保健操" , false, 1),
            InnerBreakLesson(4 , "午休" , false, 0),
            Inner2BreakLesson(1,8 , "社团活动" , false, 0),
            Inner2BreakLesson(2,8 , "社团活动" , false, 0),
            Inner2BreakLesson(3,8 , "班级的卫生区清洁" , false, 0),
            Inner2BreakLesson(4,8 , "社团活动" , false, 0),
            Inner2BreakLesson(5,8 , "校园大扫除" , false, 0),
        )).build()
    }

    class InnerLesson(val x : Int ,val y : Int , val label : String) : Lesson {
        override fun weekNo() = x

        override fun lessonIndex() = y

        override fun label() = label
    }

    class InnerBreakLesson(val y : Int , val label : String , val isBefore : Boolean, val sort : Int) :
        SerialBreakLesson {

        override fun lessonIndex() = y

        override fun label() = label

        override fun isBeforeLesson() = isBefore

        override fun sort() = sort
    }

    class Inner2BreakLesson(val x : Int , var y : Int , val label : String , val isBefore : Boolean , val sort : Int) : BreakLesson {
        override fun weekNo() = x

        override fun lessonIndex() = y

        override fun label() = label

        override fun isBeforeLesson() = isBefore

        override fun sort() = sort
    }
}