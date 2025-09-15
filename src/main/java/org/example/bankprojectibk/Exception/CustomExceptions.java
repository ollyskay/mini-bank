//package org.example.bankprojectibk.Exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class CustomExceptions {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
//        Map<String, Object> error = new HashMap<>();
//        error.put("timestamp", LocalDateTime.now());
//        error.put("error", ex.getClass().getSimpleName());
//        error.put("message", ex.getMessage());
//        error.put("trace", Arrays.toString(ex.getStackTrace())); // remove in prod
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
//        Map<String, Object> error = new HashMap<>();
//        error.put("timestamp", LocalDateTime.now());
//        error.put("error", "Bad Request");
//        error.put("message", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//    }
//
//
//
//}
