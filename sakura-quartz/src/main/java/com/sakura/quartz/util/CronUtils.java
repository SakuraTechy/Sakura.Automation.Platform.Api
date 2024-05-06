package com.sakura.quartz.util;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sakura.common.exception.BizException;
import com.sakura.system.common.SysErrorCode;
import org.quartz.CronExpression;

/**
 * cron表达式工具类
 *
 * @author liuzhi
 */
public class CronUtils {
    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 返回一个字符串值,表示该消息无效Cron表达式给出有效性
     *
     * @param cronExpression Cron表达式
     * @return String 无效时返回表达式错误描述,如果有效返回null
     */
    public static String getInvalidMessage(String cronExpression) {
        try {
            new CronExpression(cronExpression);
            return null;
        } catch (ParseException pe) {
            return pe.getMessage();
        }
    }

    /**
     * 返回下一个执行时间根据给定的Cron表达式
     *
     * @param cronExpression Cron表达式
     * @return Date 下次Cron表达式执行时间
     */
    public static Date getNextExecution(String cronExpression) {
        try {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 根据给定的Cron表达式返回最近5次运行时间
     *
     * @param cronExpression Cron表达式
     * @return runTimes 最近5次运行时间
     */
    public static List<String> getNextFiveRunTimes(String cronExpression) {
        List<String> runTimes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            CronExpression cron = new CronExpression(cronExpression);
            ZonedDateTime now = ZonedDateTime.now();
            for (int i = 0; i < 5; i++) {
                ZonedDateTime nextRunTime = ZonedDateTime.ofInstant(cron.getNextValidTimeAfter(Date.from(now.toInstant())).toInstant(), now.getZone());
                runTimes.add(formatter.format(nextRunTime));
                now = nextRunTime.plusNanos(1); // 微调到下一个纳秒，防止重复
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid cron expression", e);
        }
        return runTimes;
    }

    public static void main(String[] args) throws ParseException {
        String cronExpression = "0/1 * * * * ? ";
        List<String> times = getNextFiveRunTimes(cronExpression);
        System.out.println(times);
    }
}
