package com.sduwh.match;

import com.sduwh.match.util.StringUtils;
import org.junit.Test;

/**
 * Created by qxg on 17-7-19.
 */

public class TestNormal {
    @Test
    public void test(){
        Integer i = null;
        System.out.println(StringUtils.nullOrEmpty(String.valueOf(i)));
    }
}
