    package com.group07.PetHealthCare.exception;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;

    @ControllerAdvice
    public class GlobalException {
        @ExceptionHandler(value = RuntimeException.class)
        ResponseEntity<String> handleRuntimeException(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
            return ResponseEntity.badRequest().body(e.getFieldError().getDefaultMessage());
        }
    }
