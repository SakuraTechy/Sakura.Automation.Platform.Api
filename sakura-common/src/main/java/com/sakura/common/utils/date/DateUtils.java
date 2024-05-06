package com.sakura.common.utils.date;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 *
 * @author liuzhi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final String YYYY = "yyyy";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    public static void main(String[] args) {
        Date dateTime1Str = stringToDate("2023-12-08 00:00:02", YYYY_MM_DD_HH_MM_SS);
        Date dateTime2Str = stringToDate("2023-12-08 01:01:05", YYYY_MM_DD_HH_MM_SS);
        String millis = getDatePoor(dateTime2Str,dateTime1Str);
        System.out.println("Milliseconds: " + millis);
        String millis1 = getDatePoors(dateTime2Str,dateTime1Str);
        System.out.println("Milliseconds: " + millis1);
        long millis2= calculateTimeDifference("2023-12-08 09:29:15","2023-12-08 09:30:12", YYYY_MM_DD_HH_MM_SS);
        System.out.println("Milliseconds: " + millis2);
    }

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期字符串转换为 Date 对象
     */
    public static Date stringToDate(String dateTimeStr, String format) {
        try {
            // 创建 SimpleDateFormat 对象，并设置日期时间格式
            SimpleDateFormat formatter = new SimpleDateFormat(format);

            // 使用 SimpleDateFormat 将字符串转换为 Date 对象
            return formatter.parse(dateTimeStr);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse date", e);
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2)
    {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
         long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
         long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
    }

    public static String getDatePoors(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();

        StringBuilder result = new StringBuilder();

        // 计算差多少天
        long day = diff / nd;
        if (day > 0) {
            result.append(day).append("天");
        }

        // 计算差多少小时
        long hour = diff % nd / nh;
        if (hour > 0 || result.length() > 0) {
            result.append(hour).append("小时");
        }

        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        if (min > 0 || result.length() > 0) {
            result.append(min).append("分钟");
        }

        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        result.append(sec).append("秒");

        return result.toString();
    }

    public static long calculateTimeDifference(String dateTime1Str, String dateTime2Str, String format) {
        // 创建 DateTimeFormatter 对象，用于解析日期和时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        // 将字符串转换为 LocalDateTime 对象
        LocalDateTime dateTime1 = LocalDateTime.parse(dateTime1Str, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(dateTime2Str, formatter);

        // 使用 Duration.between() 方法计算两者之间的差距
        Duration duration = Duration.between(dateTime1, dateTime2);

        // 获取并返回以毫秒为单位的差距
        return duration.toMillis();
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        if (null == date) {
            return "";
        }
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static String addDate(Object str, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String dateStr = "";
        try {
            Date date = parseDate(str.toString(), parsePatterns);
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            cl.add(Calendar.DATE, days);
            dateStr = sdf.format(cl.getTime());
        } catch (ParseException e) {
            return "";
        }
        return dateStr;
    }

    public static String getAge(Date birthday) {
        String start = formatDate(birthday, "yyyy-MM-dd");
        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String stop = sim.format(new java.util.Date());
        int zs = (Integer.parseInt(stop.substring(0, 4)) - Integer.parseInt(start.substring(0, 4)));
        return zs + "";
    }

    /**
     * 获取本月第一天
     *
     * @return String
     * @throws
     */
    public static String getFirstDay() {
        Calendar cale = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday = "";
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        return firstday;
    }

    /**
     * 获取本月最后一天
     *
     * @return String
     * @throws
     */
    public static String getLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String lastday = "";
        Calendar cale = null;
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        return lastday;
    }

    /**
     * 获取指定日期的 前几天或者后几天
     *
     * @param today
     * @param data
     * @return
     */
    public static String getNextDay(Date today, int data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, data);
        today = calendar.getTime();
        return DateFormatUtils.format(today, "yyyy-MM-dd");
    }

    /**
     * 获取指定日期的 前几天或者后几天
     *
     * @param today
     * @param data
     * @return
     */
    public static Date getNextDayDate(Date today, int data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, data);
        today = calendar.getTime();
        return today;
    }

    public static String StringDate(String birthday) {
        Date date;
        String now = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse("20160806204214913");
            now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 判断两个日期那个大。
     *
     * @param d1 today 大于  data 返回  false
     * @param d1  data 大于  today 返回  true
     * @return
     */
    public static boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int result = c1.compareTo(c2);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor)
    {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor)
    {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }
}
