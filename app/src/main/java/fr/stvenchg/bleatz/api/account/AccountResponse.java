package fr.stvenchg.bleatz.api.account;

public class AccountResponse {
    private boolean email_verified;
    private boolean phone_verified;
    private String phone;
    private String firstname;

    private String lastname;

    private String idUser;

    private String email;

    private String creation_date;

    private String address;

    private String role;
    private boolean success;
    private String role;

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
    public String getLastname() {
        return lastname;
    }
    public String getUserId() {
        return idUser;
    }
    public String getEmail() {
        return email;
    }
    public String getCreationDate() {
        return creation_date;
    }
    public String getAddress() {
        return address;
    }
    public String getRole() {
        return role;
    }
}

