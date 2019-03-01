package com.biubiu.rpc.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author yule.zhang
 * @date 2019/2/23 0:40
 * @email zhangyule1993@sina.com
 * @description 工具类
 */
public class StringEx {
    private static final Logger logger = LoggerFactory.getLogger(StringEx.class);

    public StringEx() {
    }

    public static final String newRid(String serial) {
        return newUUID() + serial;
    }

    public static final String newUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static final String safetyChar(String c) {
        try {
            c = new String(c.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException var2) {
            logger.error("safetyChar", var2);
            return "";
        }

        return c.replace("\"", "").replace("'", "");
    }

    public static final boolean stringIsNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static final <T> String stringJoin(List<T> array, String separator) {
        if (array == null) {
            return null;
        } else {
            int arraySize = array.size();
            int bufSize = arraySize == 0 ? 0 : ((array.get(0) == null ? 16 : array.get(0).toString().length()) + 1) * arraySize;
            StringBuffer buf = new StringBuffer(bufSize);

            for (int i = 0; i < arraySize; ++i) {
                if (i > 0) {
                    buf.append(separator);
                }

                if (array.get(i) != null) {
                    buf.append(array.get(i));
                }
            }

            return buf.toString();
        }
    }

    public static final String stringJoin(String[] array, String separator) {
        if (array == null) {
            return null;
        } else {
            int arraySize = array.length;
            int bufSize = arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize;
            StringBuffer buf = new StringBuffer(bufSize);

            for (int i = 0; i < arraySize; ++i) {
                if (i > 0) {
                    buf.append(separator);
                }

                if (array[i] != null) {
                    buf.append(array[i]);
                }
            }

            return buf.toString();
        }
    }

    public static final List<String> stringToList(String s) {
        if (s != null && !s.isEmpty()) {
            List<String> list = new ArrayList();
            String[] str = s.split(";");
            String[] var3 = str;
            int var4 = str.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String string = var3[var5];
                list.add(string);
            }

            return list;
        } else {
            return new LinkedList();
        }
    }

    public static final String removeStartChar(String str, String c) {
        return str.length() > 1 && str.startsWith(c) ? str.substring(1) : str;
    }

    public static final String removeEndChar(String str, String c) {
        return str.length() <= 1 && !str.endsWith(c) ? str : str.substring(0, str.length() - 1);
    }

    public static final String cleanUpSQL(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("'", "");
        str = str.replaceAll(";", "");
        str = str.replaceAll("--", "");
        str = str.replaceAll("/", "");
        str = str.replaceAll("%", "");
        return str;
    }

    /**
     * 校验数据是否是json格式
     * @param data
     * @return
     */
    public static final boolean isJson(String data) {
        return checkIsJson(data);
    }

    private static final boolean checkIsJson(String data){
        if(null == data){
            return false;
        }
        try{
            JSONObject.parseArray(data);
        } catch(JSONException e) {
            try{
                JSONObject.parseObject(data);
            } catch(JSONException ex) {
                return false;
            }
            return true;
        }
        return true;
    }
}
