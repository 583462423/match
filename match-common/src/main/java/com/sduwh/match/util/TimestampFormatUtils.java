package com.sduwh.match.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by qxg on 17-7-22.
 * 日期转换工具
 */
public class TimestampFormatUtils {
    public static Timestamp getTimestampFromString(String format, String time){
        SimpleDateFormat sdf = new SimpleDateFormat(format  );
        try {
            return new Timestamp(sdf.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
