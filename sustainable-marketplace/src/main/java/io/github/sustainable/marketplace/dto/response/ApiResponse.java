package io.github.sustainable.marketplace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;

    public ApiResponse(String s, T userDto) {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}