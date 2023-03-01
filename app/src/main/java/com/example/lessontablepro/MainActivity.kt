package com.example.lessontablepro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jaygee.lessonschedule.LessonScheduleProView
import com.jaygee.lessonschedule.model.BreakLesson
import com.jaygee.lessonschedule.model.Lesson
import com.jaygee.lessonschedule.model.SerialBreakLesson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val table = findViewById<LessonScheduleProView>(R.id.table)
        table.setLessonData(listOf(
            InnerLesson(1,1,"art-room331-wasabee"),
            InnerLesson(2,2,"math-room123-J.Koan"),
        )).setSerialBreakLessonData(listOf(
            InnerBreakLesson(1 , "running" , true , 1),
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