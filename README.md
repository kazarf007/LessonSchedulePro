# LessonSchedulePro


1.支持缩放查看

2.支持横纵双向同时拖动查看

3.支持 星期/周 头 不受拖动影响固定显示

4.支持“周课间”配置：

      1.主要参数是第x节课，和星期几无关：例如配置第2节课后是课间操，那么每天第2节课之后都是课间操。
      2.支持在 第x节课之前/第x节课之后 同时配置“周课间”。
      3.支持“周课间”叠加配置：例如：第1节课之前叠加配置 升国旗 和 早操。

5.支持“星期课间”配置：

      1 参数是 星期x ，第y节课：例如配置周一，周二，周四的第7节课之后是社团活动，但是周三第7节课之后是班会，周五第7节课之后是校园大扫除。
      2 支持在 第x节课之前/第x节课之后 同时配置“周课间”。
      3 支持“星期课间”叠加配置。
      4 支持按星期非全量配置：例如只配置星期一的第2节课后是升旗仪式。

6. 支持“周课间”“星期课间”混合配置（例如第一节课之前先是“周课间”统一晨跑，然后是“星期课间”按星期？不同科目的早读，然后又是“周课间”统一早餐时间）

注：1.文字颜色，背景颜色支持定义。
    2.超长文字支持根据宽度自动换行显示。
    
属性：

      // 格子的宽度

      <attr name="cellWidth" format="dimension"/> 

      //星期数 

      <attr name="weekNo" format="integer"/>  

      //每日课程最大数 

      <attr name="lessonMaxNo" format="integer"/>   

      //cell之间的间距 

      <attr name="dividerSize" format="dimension"/> 

      //cell中文字的左右间距 

      <attr name="textPaddingSize" format="dimension"/>   
     
usage:

      添加依赖
      implementation 'io.github.kazarf007:lessonschedual:1.0.0'
      
      xml
      <com.jaygee.lessonschedule.LessonScheduleProView
        android:id="@+id/table"
        app:dividerSize="10dp"
        app:textPaddingSize="10dp"
        app:weekNo="7"
        app:lessonMaxNo="8"
        app:cellWidth="100dp"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

      activity
      
      val table = findViewById<LessonScheduleProView>(R.id.table)
      
      table.setLessonData(listOf(InnerLesson(1,1,"art-room331-wasabee"),InnerLesson(2,2,"math-room123-J.Koan")))
           .setSerialBreakLessonData(listOf(InnerBreakLesson(1 , "running" , true , 1)))
           .build()
        


https://user-images.githubusercontent.com/23413753/222059039-17ab924a-b330-4172-aaeb-651d065609a0.mp4

