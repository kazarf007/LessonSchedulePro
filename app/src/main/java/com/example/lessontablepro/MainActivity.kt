package com.example.lessontablepro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jaygee.lessonschedule.LessonScheduleProView
import com.jaygee.lessonschedule.model.BreakLesson
import com.jaygee.lessonschedule.model.Lesson
import com.jaygee.lessonschedule.model.NOT_MATTER_WEEK
import com.jaygee.lessonschedule.model.SerialBreakLesson
import com.jaygee.lessonschedule.util.ScheduleClickListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val table = findViewById<LessonScheduleProView>(R.id.table)
        table
            .configDrawer(lessonDrawer = MyLessonDrawer(this) ,
                breakLessonDrawer = MyBreakDrawer(this))
            .configScheduleSize(5, 5)
            .setLessonData(
                listOf(
                    InnerLesson(2, 1, "okkk1"),
                    InnerLesson(2, 3, "math-room123-J.Koan"),
                    InnerLesson(1, 1, "small"),
                    InnerLesson(2, 2, "sport-room321-J.Koan"),
                    InnerLesson(6, 2, "sport-room123-J.Koan"),
                )
            )
            .setSerialBreakLessonData(
                listOf(
                    InnerBreakLesson(1, "running", true, 1),
                    InnerBreakLesson(1, "gymnastics", true, 3),
                    Inner2BreakLesson(1, 2, "music", true, 1),//星期1，第2节之前 1
                    Inner2BreakLesson(2, 2, "dance", true, 0),//星期2，第2节之前 0
                    Inner2BreakLesson(2, 2, "dance222222222222222222", true, 1),//星期2，第2节之前 1
                    Inner2BreakLesson(3, 2, "painting", true, 3),//星期3，第2节之前 3
                    Inner2BreakLesson(4, 2, "balls", true, 4),//星期4，第2节之前 4
                )
            )
            .addClickListener(object : ScheduleClickListener {
            override fun clickLesson(weekNo: Int, lessonIndexNo: Int) {
                Toast.makeText(this@MainActivity ,
                "点击正课:星期${weekNo}第${lessonIndexNo}节", Toast.LENGTH_SHORT).show()
            }

            override fun clickBreakLesson(
                weekNo: Int,
                lessonIndexNo: Int,
                isBefore: Boolean,
                sort: Int
            ) {
                if (weekNo == NOT_MATTER_WEEK){
                    Toast.makeText(this@MainActivity ,
                        "点击课间:第${lessonIndexNo}节 ${if (isBefore) "之前" else "之后" } 第${sort}个", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity ,
                        "点击课间:星期${weekNo} 第${lessonIndexNo}节 ${if (isBefore) "之前" else "之后" } 第${sort}个", Toast.LENGTH_SHORT).show()
                }
            }
        })
            .build()
    }

}