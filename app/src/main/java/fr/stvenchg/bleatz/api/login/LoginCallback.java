package fr.stvenchg.bleatz.api.login;

public interface LoginCallback {
    void onLoginSuccess();
    void onLoginFailure(String message);
}
