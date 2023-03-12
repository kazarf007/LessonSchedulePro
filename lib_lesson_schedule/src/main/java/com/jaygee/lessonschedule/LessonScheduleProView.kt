package com.jaygee.lessonschedule

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import com.jaygee.lessonschedual.R
import com.jaygee.lessonschedule.drawer.*
import com.jaygee.lessonschedule.drawer.base.BorderDrawer
import com.jaygee.lessonschedule.drawer.base.BreakDrawer
import com.jaygee.lessonschedule.drawer.base.LessonDrawer
import com.jaygee.lessonschedule.model.*
import com.jaygee.lessonschedule.util.*

/**
 *  create on 13/2/2023
 **/
class LessonScheduleProView : View, View.OnTouchListener {

    //星期坐标记录 key -> 星期?  value -> y起点坐标
    private val weekStartAxisY = mutableMapOf<Int, Float>()

    private val texts = mutableMapOf<Pair<Int, Int>, List<String>>()

    //课程
    private val lessons = mutableListOf<Lesson>()

    //课程坐标记录 key -> 第?节 value -> y起点坐标
    private val lessonStartAxisY = mutableMapOf<Int, Float>()

    //格子 key -> x,y星期节数坐标 ， value -> 课程
    private val cells = mutableMapOf<Pair<Int, Int>, LessonCell>()

    //课间活动
    // key -> first 第?节课 / second 课前/课后
    // value ->   key : 显示排序 ， value : 同一个排序下的课间数组
    private val serialBreakLessons =
        mutableMapOf<Pair<Int, Boolean>, Map<Int, List<BreakLesson>>>()

    //课间 key -> 第?节 & 课前/课后  value ->  包含的课间数组 ( key -> 排序  value -> 同一个排序下的课程 )
    private val breakCell = mutableMapOf<Pair<Int, Boolean>, Map<Int, List<BreakLessonCell>>>()

    //课前课间的高度记录 key -> 第?节 & 课前/课后  value -> 每一行的高度
    private val blHeightList1 = mutableMapOf<Pair<Int, Boolean>, List<Float>>()

    //课后课间的高度记录 key -> 第?节 & 课前/课后  value -> 每一行的高度
    private val blHeightList2 = mutableMapOf<Pair<Int, Boolean>, List<Float>>()


    //星期 start form 1
    private var weekNo = 5

    //最大节数  start form 1
    private var lessonMaxNo = 8

    //格子之间的间隔
    private var dividerSize = 0f

    private var textPaddingSize = 0f


    //课表边框绘制
    private lateinit var borderDrawer: BorderDrawer

    //课程绘制
    private lateinit var drawer: LessonDrawer


    private lateinit var breakDrawer: BreakDrawer

    //格子的宽高
    private var cellWidth = 0f
    private var cellHeight = 0

    //实际宽度高度
    private var exactlyWidth = 0
    private var exactlyHeight = 0

    //最大滑动距离
    private var maxScrollWidth = 0
    private var maxScrollHeight = 0

    //缩放系数
    private var mScaleFactor = 1f
        set(value) {
            field = value
            maxScrollWidth = (exactlyWidth * field - measuredWidth).toInt()
            maxScrollHeight = (exactlyHeight * field - measuredHeight).toInt()
        }

    //正在缩放
    private var isScaling = false

    //多指触控
    private var isMultiFingers = false

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            isScaling = true

            mScaleFactor *= detector.scaleFactor

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f))

            invalidate()
            return true
        }
    }

    private val mScaleDetector = ScaleGestureDetector(context, scaleListener)

    private var clickListener: ScheduleClickListener? = null

    constructor(context: Context) : super(context) {
        cellWidth = 100f
        weekNo = 5
        lessonMaxNo = 8
        setOnTouchListener(this)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        context.obtainStyledAttributes(attr, R.styleable.LessonScheduleProView).apply {
            cellWidth = getDimension(R.styleable.LessonScheduleProView_cellWidth, 100f)
            weekNo = getInteger(R.styleable.LessonScheduleProView_weekNo, 5)
            lessonMaxNo = getInteger(R.styleable.LessonScheduleProView_lessonMaxNo, 8)
            dividerSize = getDimension(R.styleable.LessonScheduleProView_dividerSize, 0f)
            textPaddingSize = getDimension(R.styleable.LessonScheduleProView_textPaddingSize, 10f)
        }.recycle()
        setOnTouchListener(this)
    }

    fun addClickListener(l: ScheduleClickListener?): LessonScheduleProView {
        clickListener = l
        return this
    }


    fun configScheduleSize(xCount: Int, yCount: Int): LessonScheduleProView {
        weekNo = xCount
        lessonMaxNo = yCount
        return this
    }

    fun configDrawer(
        borderDrawer: BorderDrawer? = null,
        lessonDrawer: LessonDrawer? = null,
        breakLessonDrawer: BreakDrawer? = null
    ): LessonScheduleProView {
        if (borderDrawer != null) {
            this.borderDrawer = borderDrawer
        }
        if (lessonDrawer != null) {
            this.drawer = lessonDrawer
        }
        if (breakLessonDrawer != null) {
            this.breakDrawer = breakLessonDrawer
        }
        return this
    }

    fun setLessonData(data: List<Lesson>): LessonScheduleProView {
        lessons.clear()
        lessons.addAll(data.filter { it.weekNo() in 1..weekNo && it.lessonIndex() in 1..lessonMaxNo })
        return this
    }


    fun setSerialBreakLessonData(data: List<BreakLesson>): LessonScheduleProView {
        serialBreakLessons.clear()
        //Map<Pair<Int, Boolean>, Map<Int , List<BreakLesson>>>
        //组合成数据格式是：
        // <第1节，课前> = [0,<晨跑，晨操>],[1,<<语文<周一>，英语<周二>...>>]
        // <第1节，课后> = [0,<周课间,星期课间>]
        for (entry in data.filter { (if (it.weekNo() == NOT_MATTER_WEEK) true else it.weekNo() in 1..weekNo) && it.lessonIndex() in 1..lessonMaxNo }
            .groupBy(keySelector = { bl -> bl.lessonIndex() })) {
            entry.value.groupBy { bl -> bl.isBeforeLesson() }.forEach { entry2 ->
                serialBreakLessons[Pair(entry.key, entry2.key)] =
                    entry2.value.asSequence().sortedBy { it.sort() }.groupBy { it.sort() }
            }
        }
        return this
    }

    fun build() {
        if (!::borderDrawer.isInitialized) {
            borderDrawer = DefaultBorderDrawer(
                tvSize = context.resources.getDimension(R.dimen.tv_size),
                color = Color.GRAY,
                marginX = context.resources.getDimension(R.dimen.dp50),
                marginY = context.resources.getDimension(R.dimen.dp30)
            )
        }
        if (!::drawer.isInitialized) {
            drawer = DefaultLessonDrawer(
                tvSize = context.resources.getDimension(R.dimen.tv_size)
            )
        }
        if (!::breakDrawer.isInitialized) {
            breakDrawer = SerialBreakDrawer(
                tvSize = context.resources.getDimension(R.dimen.tv_size),
                minHeight = context.resources.getDimension(R.dimen.dp30)
            )
        }
        resetPicture()
        requestLayout()
    }

    /**
     * 计算同一行文字的最大的宽度
     */
    private fun <T> List<T>.calculateWidth(
        paint: Paint,
        transformWord: (T) -> String,
        result: (Float) -> Unit,
        other: (T) -> Unit = {}
    ) {
        var tmpMaxWidth = 0f
        var tmpWidth = 0f
        for (lesson in this) {
            tmpWidth = paint.measureText(transformWord(lesson))
            if (tmpMaxWidth < tmpWidth) {
                tmpMaxWidth = tmpWidth
            }
            other(lesson)
        }
        result(tmpMaxWidth)
    }


    /**
     * 文字最大绘制宽度
     */
    private val textMaxDrawWidth: Float by lazy { cellWidth - textPaddingSize * 2 }


    /**
     * 计算课间的高度
     */
    private fun calculateBreakLessonHeight(
        key: Pair<Int, Boolean>,
        blHeight: MutableMap<Pair<Int, Boolean>, List<Float>>,
        contains: () -> Unit,
        notContains: () -> Unit
    ) {
        if (serialBreakLessons.containsKey(key)) {
            val li = mutableListOf<Float>()
            serialBreakLessons[key]!!.forEach { // sort , list
                if (it.value.any { v -> v.weekNo() != NOT_MATTER_WEEK }) {
                    it.value.calculateWidth(breakDrawer.paint(),
                        transformWord = { it.label() },
                        result = { max ->
                            val ss = if (max > textMaxDrawWidth) {
                                ((max / cellWidth) + 1) * breakDrawer.minHeight()
                            } else {
                                breakDrawer.minHeight()
                            }
                            li.add(ss)
                        })
                } else {
                    li.add(breakDrawer.minHeight())
                }
            }
            blHeight[key] = li
            contains()
        } else {
            notContains()
        }
    }

    private fun resetPicture() {
        exactlyWidth =
            (weekNo * cellWidth + borderDrawer.xBorderSize() + 2 * borderDrawer.lineSize() + dividerSize * (weekNo + 1)).toInt()
        cells.clear()
        lessonStartAxisY.clear()
        weekStartAxisY.clear()
        breakCell.clear()
        texts.clear()
        blHeightList1.clear()
        blHeightList2.clear()
        cellHeight = 0
        //通过比较获取最大长度的课程名字
        lessons.calculateWidth(paint = drawer.paint(),
            transformWord = { it.label() }, other = {
                texts[Pair(it.weekNo(), it.lessonIndex())] =
                    it.label().generateMultiLineString(drawer.paint(), textMaxDrawWidth)
            }, result = { tmpMaxWidth ->
                //获取文字的真实显示行数
                val line = tmpMaxWidth / textMaxDrawWidth
                //动态计算课程格子的最大高度
                cellHeight = (line * textMaxDrawWidth + cellWidth / 5).toInt()
            })
        //课间高度缓存（针对课前）
        val blHeight = mutableMapOf<Int, Float>()
        //课间高度缓存（针对课后）
        val blHeight2 = mutableMapOf<Int, Float>()

        //课间高度之和
        var blDisSum = 0f
        var blDisSum2 = 0f
        for (x in 1..weekNo) {
            val l =
                borderDrawer.xBorderSize() + borderDrawer.lineSize() + dividerSize + (dividerSize + cellWidth) * (x - 1)
            val r = l + cellWidth
            weekStartAxisY[x] =
                borderDrawer.xBorderSize() + borderDrawer.lineSize() + dividerSize + (dividerSize + cellWidth) * (x - 1)
            for (y in 1..lessonMaxNo) {
                //缓存每节课的课前课间的高度和
                if (!blHeight.containsKey(y)) {
                    calculateBreakLessonHeight(Pair(y, true), blHeightList1, {
                        blDisSum += blHeightList1[Pair(
                            y,
                            true
                        )]!!.sum() + dividerSize * blHeightList1[Pair(y, true)]!!.size
                    }, {
                        blDisSum += 0
                    })
                    blHeight[y] = blDisSum
                }
                //缓存每节课的课后课间的高度和
                if (!blHeight2.containsKey(y)) {
                    calculateBreakLessonHeight(Pair(y, false), blHeightList2, {
                        blDisSum2 += blHeightList2[Pair(
                            y,
                            false
                        )]!!.sum() + dividerSize * blHeightList2[Pair(y, false)]!!.size
                    }, {
                        blDisSum2 += 0
                    })
                    blHeight2[y] = blDisSum2
                }
                //缓存每节课格子的起点y坐标
                if (!lessonStartAxisY.containsKey(y)) {
                    lessonStartAxisY[y] =
                        borderDrawer.yBorderSize() + borderDrawer.lineSize() + dividerSize + (dividerSize + cellHeight) * (y - 1) + try {
                            blHeight[y]!!.toFloat()
                        } catch (e: Exception) {
                            0f
                        } + try {
                            blHeight2[y - 1]!!.toFloat()
                        } catch (e: Exception) {
                            0f
                        }
                }
                //缓存每个课间的cell
                if (breakCell.none { it.key.first == y } &&
                    (serialBreakLessons.containsKey(Pair(y, true)) ||
                            serialBreakLessons.containsKey(Pair(y, false)))) {

                    if (serialBreakLessons.containsKey(Pair(y, true))) {
                        //课前
                        addBreakCell(Pair(y, true), blHeightList1) {
                            lessonStartAxisY[y]!! - blHeightList1[it]!!.sum() - dividerSize * blHeightList1[it]!!.size
                        }
                    }

                    if (serialBreakLessons.containsKey(Pair(y, false))) {
                        //课后
                        addBreakCell(Pair(y, false), blHeightList2) {
                            lessonStartAxisY[y]!! + dividerSize + cellHeight
                        }
                    }

                }

                val cell = LessonCell(
                    RectF(l, lessonStartAxisY[y]!!, r, (lessonStartAxisY[y]!! + cellHeight))
                )
                cell.label.clear()
                cell.label.addAll(texts[Pair(x,y)] ?: listOf())
                cells[Pair(x, y)] = cell
            }
        }


        exactlyHeight =
            (
                    borderDrawer.lessonIndexMargin() + //标题高度
                            cellHeight * lessonMaxNo + // 正课高度
                            blHeightList1.values.sumOfFloat { it.sum() } + blHeightList2.values.sumOfFloat { it.sum() } + // 课间高度
                            dividerSize * (lessonMaxNo + 1 + breakCell.values.sumOf { it.values.size })
                    ).toInt()

    }

    /**
     * 添加课间
     */
    private fun addBreakCell(
        key: Pair<Int, Boolean>,
        blHeight: Map<Pair<Int, Boolean>, List<Float>>,
        calculateTop: (Pair<Int, Boolean>) -> Float
    ) {
        val map = mutableMapOf<Int, MutableList<BreakLessonCell>>()
        var ttop = calculateTop(key)
        val li = mutableListOf<BreakLessonCell>()
        val sbl = serialBreakLessons[key]!!
        var indexHeight = 0f
        sbl.keys.forEachIndexed { index, sortKey ->
            indexHeight = blHeight[key]!![index]

            if (sbl[sortKey]!!.any { it.weekNo() != NOT_MATTER_WEEK }) {
                sbl[sortKey]!!.forEach { bkl ->
                    li.add(
                        generateBreakLessonCell(
                            weekNo = bkl.weekNo(), lessonIndexNo = key.first, sort = sortKey,
                            label = bkl.label(),
                            rectF = RectF(
                                borderDrawer.xBorderSize() + borderDrawer.lineSize() + dividerSize + (cellWidth + dividerSize) * (bkl.weekNo() - 1),
                                ttop,
                                borderDrawer.xBorderSize() + borderDrawer.lineSize() + dividerSize + (cellWidth + dividerSize) * (bkl.weekNo() - 1) + cellWidth,
                                ttop + indexHeight
                            )
                        )
                    )
                }
            } else {
                li.add(
                    generateBreakLessonCell(
                        weekNo = NOT_MATTER_WEEK, lessonIndexNo = key.first, sort = sortKey,
                        label = sbl[sortKey]?.first()?.label() ?: "",
                        rectF = RectF(
                            borderDrawer.xBorderSize() + borderDrawer.lineSize() + dividerSize,
                            ttop,
                            exactlyWidth - dividerSize,
                            ttop + indexHeight
                        )
                    )
                )
            }
            ttop += (indexHeight + dividerSize)
            map[sortKey] = li
        }
        breakCell[key] = map
    }

    private fun generateBreakLessonCell(
        weekNo: Int,
        lessonIndexNo: Int,
        sort: Int,
        label: String,
        rectF: RectF
    ): BreakLessonCell {
        return BreakLessonCell(rectF).apply {
            this.week = weekNo
            this.lessonIndex = lessonIndexNo
            this.sort = sort
            this.label.let {
                it.clear()
                it.addAll(
                    label.generateMultiLineString(
                        breakDrawer.paint(),
                        cellWidth - textPaddingSize * 2
                    )
                )
            }
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.scale(
            mScaleFactor,
            mScaleFactor
        )

        canvas?.drawColor(Color.WHITE)

        breakDrawer.drawBreak(canvas, breakCell)

        for (cell in cells) {
            drawer.draw(
                canvas,
                cell.value,
                (texts[cell.key] ?: mutableListOf())
            )
        }
        borderDrawer.drawWeek(
            canvas,
            borderDrawer.xBorderSize(),
            exactlyWidth.toFloat(),
            exactlyHeight.toFloat(),
            cellWidth.toInt(),
            weekStartAxisY,
            mScaleFactor,
            scrollX,
            scrollY
        )
        borderDrawer.drawIndex(
            canvas,
            borderDrawer.yBorderSize(),
            exactlyHeight.toFloat(),
            exactlyWidth.toFloat(),
            cellHeight.toFloat(),
            textPaddingSize,
            lessonStartAxisY,
            mScaleFactor,
            scrollX,
            scrollY
        )
        borderDrawer.drawXYCrossCover(
            canvas, mScaleFactor,
            scrollX,
            scrollY
        )

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
        maxScrollWidth = exactlyWidth - measuredWidth
        maxScrollHeight = exactlyHeight - measuredHeight
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        return when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> MeasureSpec.getSize(widthMeasureSpec)
            else -> exactlyWidth
        }
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        return when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> MeasureSpec.getSize(heightMeasureSpec)
            else -> exactlyHeight
        }
    }

    private var oldX = 0f
    private var oldY = 0f


    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        mScaleDetector.onTouchEvent(event)
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                ClickJudger.recordPressDownTime()
                oldX = event.getX(0)
                oldY = event.getY(0)
                isMultiFingers = false
                isScaling = false
            }
            MotionEvent.ACTION_UP -> {
                if (ClickJudger.isClickEvent()) {
                    findClickPos = false
                    for (cell in cells) {
                        if (ClickJudger.isClickPath(
                                cell.value.path,
                                (oldX + scrollX) / mScaleFactor,
                                (oldY + scrollY) / mScaleFactor
                            )
                        ) {
                            clickListener?.clickLesson(cell.key.first, cell.key.second)
                            findClickPos = true
                            break
                        }
                    }
                    if (!findClickPos) {
                        all@ for (mutableEntry in breakCell) {
                            for (entry in mutableEntry.value) {
                                for (breakLessonCell in entry.value) {
                                    if (ClickJudger.isClickPath(
                                            breakLessonCell.path,
                                            (oldX + scrollX) / mScaleFactor,
                                            (oldY + scrollY) / mScaleFactor
                                        )
                                    ) {
                                        clickListener?.clickBreakLesson(
                                            breakLessonCell.week, breakLessonCell.lessonIndex,
                                            mutableEntry.key.second, breakLessonCell.sort
                                        )
                                        findClickPos = true
                                        break@all
                                    }
                                }
                            }
                        }
                    }
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (event.pointerCount == 2) {
                    isMultiFingers = true
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                if (event.pointerCount == 2) {
                    scrollControl(maxScrollWidth , maxScrollHeight)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isMultiFingers) {
                    return true
                }
                scrollBy(((oldX - event.getX(0))).toInt(), ((oldY - event.getY(0))).toInt())
                ClickJudger.recordMove(oldX - event.getX(0), oldY - event.getY(0))
                oldX = event.getX(0)
                oldY = event.getY(0)
                scrollControl(maxScrollWidth , maxScrollHeight)
            }
        }
        return true
    }

    private var findClickPos = false



    // 10  20 15
    private fun getScaleCenter(event: MotionEvent): Pair<Float, Float> {
        return Pair(
            (event.getX(0) + event.getX(1)) / 2,
            (event.getY(0) + event.getY(1)) / 2
        )
    }
}


