package com.jaygee.lessonschedule.util

/**
 *  create on 28/2/2023
 **/

inline fun <T> Collection<T>.sumOfFloat(transform: (T) -> Float): Float {
    var result = 0f
    for (ts in this) {
        result += transform(ts)
    }
    return result
}


inline fun <T> Collection<T>.sumOfInt(transform: (T) -> Int): Int {
    var result = 0
    for (ts in this) {
        result += transform(ts)
    }
    return result
}