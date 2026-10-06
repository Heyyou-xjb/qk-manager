package com.itheima.utils;

public class UserHolder {
    private static final ThreadLocal<Integer> USER_HOLDER = new ThreadLocal<>();

    public static void setUserId(Integer userId) {
        USER_HOLDER.set(userId);
    }

    public static Integer getUserId() {
        return USER_HOLDER.get();
    }

    public static void removeUserId() {
        USER_HOLDER.remove();
    }
}
