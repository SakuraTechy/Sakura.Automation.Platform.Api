package com.sakura.common.annotation;

import com.sakura.common.constant.Constants;
import com.sakura.common.enums.LimitType;

import java.lang.annotation.*;

/**
 * 限流注解
 * 
 * @author liuzhi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter
{
    /**
     * 限流key
     */
    public String key() default Constants.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    public int time() default 60;

    /**
     * 限流次数
     */
    public int count() default 100;

    /**
     * 限流类型
     */
    public LimitType limitType() default LimitType.DEFAULT;
}
