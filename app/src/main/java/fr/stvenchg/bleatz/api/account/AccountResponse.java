package fr.stvenchg.bleatz.api.account;

public class AccountResponse {
    private boolean email_verified;
    private boolean success;
    public boolean isSuccess() {
        return success;
    }
    public boolean isEmailVerified() {
        return email_verified;
    }

    public void setEmailVerified(boolean email_verified) {
        this.email_verified = email_verified;
    }
}

