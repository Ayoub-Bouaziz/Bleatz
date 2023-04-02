package fr.stvenchg.bleatz.api.refreshToken;

public class RefreshTokenRequest {
    private String email;
    private String refresh_token;

    public RefreshTokenRequest(String email, String refresh_token) {
        this.email = email;
        this.refresh_token = refresh_token;
    }
}
