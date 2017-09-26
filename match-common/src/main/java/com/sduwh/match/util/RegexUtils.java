package com.sduwh.match.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qxg on 17-9-25.
 */
public class RegexUtils {
    /** 验证email是否正确*/
    public  static boolean checkEmaile(String emaile){
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(emaile);
        //进行正则匹配
        return m.matches();
    }
}
