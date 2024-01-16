package com.enndfp.simpleframework.utils;

/**
 * @author Enndfp
 */
public class ConverterUtil {

    /**
     * 返回基本数据类型的空值
     * 需要特殊处理的基本类型即int\double\short\long\byte\float\boolean
     *
     * @param type
     * @return
     */
    public static Object primitiveNull(Class<?> type) {
        if (type == int.class || type == double.class ||
                type == short.class || type == long.class ||
                type == byte.class || type == float.class) {
            return 0;
        } else if (type == boolean.class) {
            return false;
        }
        return null;
    }

    /**
     * String类型转换成对应的参数类型
     *
     * @param type              参数类型
     * @param requestParamValue 参数值
     * @return 转换后的Object
     */
    public static Object convert(Class<?> type, String requestParamValue) {
        if (isPrimitive(type)) {
            if (ValidationUtil.isEmpty(requestParamValue)) {
                return primitiveNull(type);
            }
            if (type.equals(int.class) || type.equals(Integer.class)) {
                return Integer.parseInt(requestParamValue);
            } else if (type.equals(String.class)) {
                return requestParamValue;
            } else if (type.equals(Double.class) || type.equals(double.class)) {
                return Double.parseDouble(requestParamValue);
            } else if (type.equals(Float.class) || type.equals(float.class)) {
                return Float.parseFloat(requestParamValue);
            } else if (type.equals(Long.class) || type.equals(long.class)) {
                return Long.parseLong(requestParamValue);
            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                return Boolean.parseBoolean(requestParamValue);
            } else if (type.equals(Short.class) || type.equals(short.class)) {
                return Short.parseShort(requestParamValue);
            } else if (type.equals(Byte.class) || type.equals(byte.class)) {
                return Byte.parseByte(requestParamValue);
            }
            return requestParamValue;
        } else {
            throw new RuntimeException("count not support non primitive type conversion yet");
        }
    }

    /**
     * 判断是否是基本数据类型(包括包装类以及String)
     *
     * @param type 参数类型
     * @return 是否为基本数据类型
     */
    private static boolean isPrimitive(Class<?> type) {
        return type == boolean.class
                || type == Boolean.class
                || type == double.class
                || type == Double.class
                || type == float.class
                || type == Float.class
                || type == short.class
                || type == Short.class
                || type == int.class
                || type == Integer.class
                || type == long.class
                || type == Long.class
                || type == String.class
                || type == byte.class
                || type == Byte.class
                || type == char.class
                || type == Character.class;
    }
}
