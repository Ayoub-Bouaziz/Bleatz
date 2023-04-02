package fr.stvenchg.bleatz.api.login;

public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private String refresh_token;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }
}