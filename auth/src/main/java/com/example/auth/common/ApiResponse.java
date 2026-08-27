package com.example.auth.common;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data
){}