package org.scau.utils;

@SuppressWarnings("all")
public class ThreadLocalUtil {
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    // 获取信息
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    // 设置信息
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    // 移除信息
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
