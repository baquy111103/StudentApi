package com.example.demo.exception;

public class CustomServiceException extends Throwable {
    public CustomServiceException(String message) {
        super(message);
    }

    // Khởi tạo với thông báo lỗi và nguyên nhân gốc rễ
    public CustomServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
