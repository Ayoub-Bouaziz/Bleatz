package fr.stvenchg.bleatz;

public interface EmailVerificationCallback {
    void onEmailVerificationDone(boolean isEmailVerified);
}