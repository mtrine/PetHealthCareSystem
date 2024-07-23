    package com.group07.PetHealthCare.exception;
    import com.group07.PetHealthCare.dto.request.ApiResponse;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;

    @ControllerAdvice
    public class GlobalException {
        @ExceptionHandler(value = RuntimeException.class)
        ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage());
            apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode());
            return ResponseEntity.badRequest().body(apiResponse);
        }
        @ExceptionHandler(value = AppException.class)
        ResponseEntity<ApiResponse> handleAppException(AppException e) {
            ErrorCode errorCode= e.getErrorCode();
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setMessage(errorCode.getMessage());
            apiResponse.setCode(errorCode.getCode());
            return ResponseEntity.badRequest().body(apiResponse);
        }

        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

            String enumKey = e.getFieldError().getDefaultMessage();
            ErrorCode errorCode= ErrorCode.valueOf(enumKey);

            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setMessage(errorCode.getMessage());
            apiResponse.setCode(errorCode.getCode());

            return ResponseEntity.badRequest().body(apiResponse);
        }
    }
