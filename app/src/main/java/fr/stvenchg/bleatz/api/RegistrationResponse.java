package fr.stvenchg.bleatz.api;

public class RegistrationResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}