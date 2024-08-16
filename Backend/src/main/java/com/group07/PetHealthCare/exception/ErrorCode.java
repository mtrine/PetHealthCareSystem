package com.group07.PetHealthCare.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
public enum ErrorCode {
    INVALID_ROLE(1004,"Invalid role",  HttpStatus.BAD_REQUEST),
    USER_EXISTED(1004, "User existed", HttpStatus.BAD_REQUEST),
    INCORRECT_EMAIL_OR_PASSWORD(1004,"Email or password is incorrect",HttpStatus.BAD_REQUEST),
    UNCATEGORIZED(1004,"Uncategorized", HttpStatus.INTERNAL_SERVER_ERROR),
    NAME_INVALID(1004,"Name must be more than 2 character", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1004,"Email should be valid", HttpStatus.BAD_REQUEST),
    PASS_INVALID(1004,"Password should be valid", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1004,"Not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1004,"YOU DO NOT HAVE PERMISSION", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1004,"Unauthenticated", HttpStatus.UNAUTHORIZED),

    ;
    private int code;
    private String message;
    private HttpStatusCode status;
    ErrorCode(int code, String message, HttpStatusCode status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }


}
