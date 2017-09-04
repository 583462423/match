package com.sduwh.match;

import com.sduwh.match.util.StringUtils;
import com.sduwh.match.util.TimestampFormatUtils;
import org.junit.Test;

/**
 * Created by qxg on 17-7-19.
 */

public class TestNormal {
    @Test
    public void test(){
        System.out.println(TimestampFormatUtils.getTimestampFromString("yyyy-MM-dd hh:mm", "2014-55-66 11:22"));
    }
}
