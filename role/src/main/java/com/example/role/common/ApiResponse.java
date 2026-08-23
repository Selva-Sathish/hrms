package com.example.role.common;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data
){}
