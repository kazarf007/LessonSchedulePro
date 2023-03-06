package com.jaygee.lessonschedule.model

/**
 *  create on 13/2/2023
 **/
interface Lesson {

    /**
     * 星期几
     */
    fun weekNo() : Int

    /**
     * 课程节数
     */
    fun lessonIndex() : Int

    /**
     * 课程名字
     */
    fun label() : String

}



interface BreakLesson{


    fun weekNo() : Int

    /**
     * 课程节数
     */
    fun lessonIndex() : Int

    /**
     * 课间活动名称
     */
    fun label() : String

    /**
     *  true 课前活动 false 课后活动
     */
    fun isBeforeLesson() : Boolean

    /**
     * 显示顺序：
     * 相当于课间的副坐标。
     * 例：1.第1节课之前的课间有3个，如果sort一样，会被认为是数据重复，后加入的会覆盖先前的数据
     *    2.周一的第1节课之后有一个课间a，sort = 2 ; 周二第1节课之后有一个课间b，sort = 1 ,那么实际显示为：a（1，1，2）和 b(2,1,1) b在a的上一行
     */
    fun sort() : Int
}

const val NOT_MATTER_WEEK = -1111

interface SerialBreakLesson : BreakLesson {
    override fun weekNo() = NOT_MATTER_WEEK
}