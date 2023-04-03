package fr.stvenchg.bleatz.api.phone.verify;

public class PhoneVerifyResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}