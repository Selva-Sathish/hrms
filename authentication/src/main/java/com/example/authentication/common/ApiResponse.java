package com.example.authentication.common;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data
){}
