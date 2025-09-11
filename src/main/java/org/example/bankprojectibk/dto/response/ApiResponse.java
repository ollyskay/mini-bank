package org.example.bankprojectibk.dto.response;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T>{
    private boolean success;
    private String message;
    private T data;
    private Instant timestamp;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }


    public static <T> ApiResponse<T> failure(String message, T data) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }


}
