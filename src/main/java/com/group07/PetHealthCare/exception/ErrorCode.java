package com.group07.PetHealthCare.exception;

public enum ErrorCode {
    INVALID_ROLE(1008,"Invalid role"),
    USER_EXISTED(1002, "User existed"),
    INCORRECT_EMAIL_OR_PASSWORD(1003,"Email or password is incorrect"),
    UNCATEGORIZED(1004,"Uncategorized"),
    NAME_INVALID(1005,"Name must be more than 2 character"),
    EMAIL_INVALID(1006,"Email should be valid"),
    PASS_INVALID(1007,"Password should be valid"),
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
