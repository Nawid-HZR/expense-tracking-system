package com.nawid.expense_tracking_system.presentation.dto.response;

public class SignUpResponse {
    private String message;

    public SignUpResponse(String message) {
        this.message = message;
    }

    public SignUpResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
