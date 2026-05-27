package com.summitcore.infrastructure.response;

public record ApiResponse<T>(String message, T data) {
}
