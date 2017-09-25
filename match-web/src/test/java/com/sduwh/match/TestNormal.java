package com.sduwh.match;

import com.sduwh.match.util.StringUtils;
import com.sduwh.match.util.TimestampFormatUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by qxg on 17-7-19.
 */

public class TestNormal {
    @Test
    public void test(){

        System.out.println(new Date(TimestampFormatUtils.getTimestampFromString("yyyy-MM-dd hh:mm", "2017-9-04 05:05").getTime()));
    }
}
