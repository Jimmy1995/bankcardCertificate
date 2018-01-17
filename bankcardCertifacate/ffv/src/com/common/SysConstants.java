package com.common;

import java.util.regex.Pattern;

public class SysConstants {
public static final String OPENCODE="OpenCode";
public static Pattern paramPattern = Pattern.compile("\\S*\\s*\\W*\\s*(:\\s*([A-Za-z0-9_-]+))");//匹配sql中的参数
public static Pattern wherestr=Pattern.compile("(or|and){1}.*?\\W\\s*(:\\s*\\S*\\s*)\\s*");//匹配where后面的带参数（:param）并以换行（\n）结尾的条件语句
public static Pattern orderbyPattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",Pattern.CASE_INSENSITIVE);
}
