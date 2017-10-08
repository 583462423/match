package com.sduwh.match;

import com.sduwh.match.util.StringUtils;
import com.sduwh.match.util.TimestampFormatUtils;
import org.junit.Test;

import java.io.File;
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
        File file = new File("/home/qxg/project/web/match/match-web/upload/apply/F73F30AFBD164F173ECE924E134DE82C.doc");
        System.out.println(file.exists());
    }
}
