package org.example.common.utils;

public class UserContext {

    // 使用 ThreadLocal 存储每个线程独立的用户信息
    private static final ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();

    // 获取当前线程的用户信息
    public static Long getUser() {
        return userThreadLocal.get();
    }

    // 设置当前线程的用户信息
    public static void setUser(Long userId) {
        userThreadLocal.set(userId);
    }

    // 清除当前线程的用户信息
    public static void clear() {
        userThreadLocal.remove();
    }
}
