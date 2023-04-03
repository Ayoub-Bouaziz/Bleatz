package fr.stvenchg.bleatz.api.account;

public class AccountResponse {
    private boolean email_verified;
    private boolean phone_verified;
    private String phone;
    private String firstname;
    private boolean success;
    public boolean isSuccess() {
        return success;
    }
    public boolean isEmailVerified() {
        return email_verified;
    }
    public boolean isPhoneVerified() {
        return phone_verified;
    }

    public String getPhone() {
        return phone;
    }
    public String getFirstname() {
        return firstname;
    }
}

