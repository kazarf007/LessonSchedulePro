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
        table.configScheduleSize(7, 8).setLessonData(
                listOf(
                    InnerLesson(2, 1, "okkkkkkkkko"),
                    InnerLesson(2, 2, "math-room123-J.Koan"),
                    InnerLesson(1, 1, "small"),
                    InnerLesson(2, 2, "small2"),
                    InnerLesson(2, 2, "math-room123-J.Koan"),
                    InnerLesson(6, 2, "math-room123-J.Koan"),
                )
            ).setSerialBreakLessonData(
                listOf(
                    InnerBreakLesson(1, "running", true, 1),
                    Inner2BreakLesson(1, 2, "art-room331-kk", false, 1),
                    Inner2BreakLesson(2, 2, "wait", true, 1),
                    Inner2BreakLesson(1, 2, "math-room331-wasabee123213ccc", true, 1),
                )
            ).build()
    }

}