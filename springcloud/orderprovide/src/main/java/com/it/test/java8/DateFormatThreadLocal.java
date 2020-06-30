package com.it.test.java8;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName DateFormatThreadLocal
 * @Description TODO
 * @Date 2020-01-16 11:39:00
 * java.time 操作日期和时间
 * java.time.chrono 时间矫正器，特殊时间格式
 * java.time.format 日期时间格式化
 **/
public class DateFormatThreadLocal {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
        protected DateFormat initialValue(){
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    public static final Date convert(String source) throws ParseException {
        return df.get().parse(source);
    }

}
