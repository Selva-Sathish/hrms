package com.example.organisation.common;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data
){}
