package com.example.user.common;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data
){}