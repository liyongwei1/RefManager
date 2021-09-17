package cn.liyongwei.util;

/**
 * 项目名 BookManager
 * <br>包名 util
 * <br>创建时间
 * <br>描述 字符串处理工具类
 *
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isEquals(String str1, String str2) {
        return str1.equals(str2);
    }
}
