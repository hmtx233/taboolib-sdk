package top.blackcat.mc.common

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/****
 * 时间格式化
 */
object DateUtils {

    internal fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return currentDateTime.format(formatter)
    }


    internal fun getDateTimeByLong(time: Long): String {
        // 将时间戳转换为LocalDateTime对象
        // 注意：这里假设时间戳是以毫秒为单位的
        val dateTime = LocalDateTime.ofEpochSecond(time / 1000, 0, ZoneOffset.UTC)
        // 定义日期时间格式
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        // 格式化日期时间为字符串
        return dateTime.format(formatter)
    }


}