package com.group07.PetHealthCare.exception;

public enum ErrorCode {
    CUSTOME_EXISTED(1002, "User existed"),
    EMAIL_INCORRECT(1001,"Email incorrect"),
    PASS_INCORRECT(1003,"Password incorrect"),
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
