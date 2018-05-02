package com.macaca.android.testing.server.xmlUtils;

import org.apache.commons.lang3.StringUtils;

public class StringUnicodeUtils {
    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);
            String str = Integer.toHexString(c);
            switch (4 - str.length()) {
                case 0:
                    unicode.append("\\u" + str);
                    break;
                case 1:
                    str = "0" + str;
                    unicode.append("\\u" + str);
                    break;
                case 2:
                case 3:
                default:
                    str = String.valueOf(c);
                    unicode.append(str);
                    break;
            }


        }
        return unicode.toString();
    }


    public static String decodeUnicode(String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    public static String unicode2String(String unicode){
        if(StringUtils.isBlank(unicode))return null;
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while((i=unicode.indexOf("\\u", pos)) != -1){
            sb.append(unicode.substring(pos, i));
            if(i+5 < unicode.length()){
                pos = i+6;
                sb.append((char)Integer.parseInt(unicode.substring(i+2, i+6), 16));
                if(pos < unicode.length() && unicode.substring(pos).indexOf("\\u") == -1){
                    sb.append(unicode.substring(pos,unicode.length()));
                }
            }
        }

        return sb.toString();
    }


    public static void main(String[] args) {

        String unicode = StringUnicodeUtils.string2Unicode("私人FMhahahhaha私人hahaha");

//        System.out.println(SortUtils.decodeUnicode(unicode));

        System.out.println(StringUnicodeUtils.unicode2String(unicode));

    }


}
