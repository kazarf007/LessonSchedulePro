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

    /**
     * 显示顺序
     * 都是第?节课之前的课间，如果显示顺序一样，会被认为是数据重复
     */
    fun sort() : Int

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
}

const val NOT_MATTER_WEEK = -1111

interface SerialBreakLesson : BreakLesson {
    override fun weekNo() = NOT_MATTER_WEEK
}