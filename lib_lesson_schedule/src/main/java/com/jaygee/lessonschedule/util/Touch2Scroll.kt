package com.jaygee.lessonschedule.util

import android.view.View

/**
 *  create on 6/3/2023
 **/

fun View.scrollControl(maxScrollWidth : Int , maxScrollHeight : Int){
    when {
        //横纵都全部在里面
        maxScrollWidth < 0 && maxScrollHeight < 0 -> {
            scrollTo(0, 0)
        }
        //横向在页面里，可以纵向滚动
        maxScrollWidth < 0 && maxScrollHeight >= 0 -> {
            when {
                scrollY < 0 -> {
                    scrollBy(-scrollX, -scrollY)
                }
                scrollY in 0 until maxScrollHeight -> {
                    scrollBy(-scrollX, 0)
                }
                scrollY >= maxScrollHeight -> {
                    scrollBy(
                        -scrollX, maxScrollHeight - scrollY
                    )
                }
            }
        }
        //纵向在页面里，可以横向滚动
        maxScrollWidth >= 0 && maxScrollHeight < 0 -> {
            when {
                scrollX < 0 -> {
                    scrollBy(-scrollX, -scrollY)
                }
                scrollX in 0 until maxScrollWidth -> {
                    scrollBy(0, -scrollY)
                }
                scrollX >= maxScrollWidth -> {
                    scrollBy(
                        maxScrollWidth - scrollX, -scrollY
                    )
                }
            }
        }
        else -> {
            //横纵都全部在外面
            when {
                scrollX < 0 -> {
                    when {
                        scrollY < 0 -> {
                            scrollTo(0, 0)
                        }
                        scrollY in 0 until maxScrollHeight -> {
                            scrollBy(-scrollX, 0)
                        }
                        else -> {
                            scrollBy(-scrollX, maxScrollHeight - scrollY)
                        }
                    }
                }

                scrollX in 0 until maxScrollWidth -> {
                    when {
                        scrollY < 0 -> {
                            scrollBy(0, -scrollY)
                        }
                        scrollY in 0 until maxScrollHeight -> {
                            scrollBy(0, 0)
                        }
                        else -> {
                            scrollBy(0, maxScrollHeight - scrollY)
                        }
                    }
                }

                else -> {
                    when {
                        scrollY < 0 -> {
                            scrollBy(maxScrollWidth - scrollX, -scrollY)
                        }
                        scrollY in 0 until maxScrollHeight -> {
                            scrollBy(maxScrollWidth - scrollX, 0)
                        }
                        else -> {
                            scrollTo(maxScrollWidth, maxScrollHeight)
                        }
                    }
                }
            }
        }
    }
}