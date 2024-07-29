package com.group07.PetHealthCare.exception;

public enum ErrorCode {
    INVALID_ROLE(1004,"Invalid role"),
    USER_EXISTED(1004, "User existed"),
    INCORRECT_EMAIL_OR_PASSWORD(1004,"Email or password is incorrect"),
    UNCATEGORIZED(1004,"Uncategorized"),
    NAME_INVALID(1004,"Name must be more than 2 character"),
    EMAIL_INVALID(1004,"Email should be valid"),
    PASS_INVALID(1004,"Password should be valid"),
    NOT_FOUND(1004,"Not found"),
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
