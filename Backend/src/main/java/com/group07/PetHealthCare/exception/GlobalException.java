    package com.group07.PetHealthCare.exception;
    import com.group07.PetHealthCare.dto.request.ApiResponse;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.AccessDeniedException;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;

    @ControllerAdvice
    public class GlobalException {
        @ExceptionHandler(value = RuntimeException.class)
        ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setMessage(e.getMessage());
            apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode());
            return ResponseEntity.badRequest().body(apiResponse);
        }
        @ExceptionHandler(value = AppException.class)
        ResponseEntity<ApiResponse> handleAppException(AppException e) {
            ErrorCode errorCode= e.getErrorCode();
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setMessage(errorCode.getMessage());
            apiResponse.setCode(errorCode.getCode());
            return ResponseEntity
                    .status(errorCode.getStatus())
                    .body(apiResponse);
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

        @ExceptionHandler(value = AccessDeniedException.class)
        ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e) {
            ErrorCode errorCode= ErrorCode.UNAUTHORIZED;
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(errorCode.getMessage());
            return ResponseEntity.status(errorCode.getStatus()).body(apiResponse);
        }
    }
