package fr.stvenchg.bleatz.api.refreshToken;

public class RefreshTokenResponse {
    private boolean success;
    private String message;
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
