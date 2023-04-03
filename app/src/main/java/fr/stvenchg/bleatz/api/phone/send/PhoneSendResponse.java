package fr.stvenchg.bleatz.api.phone.send;

public class PhoneSendResponse {
    private boolean success;
    private String message;
    private String phone;
    private String code_expiration;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getPhone() {
        return phone;
    }

    public String getCodeExpiration() {
        return code_expiration;
    }
}