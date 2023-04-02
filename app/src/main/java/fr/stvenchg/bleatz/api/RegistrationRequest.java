package fr.stvenchg.bleatz.api;

public class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String password_again;

    public RegistrationRequest(String firstname, String lastname, String email, String password, String password_again) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.password_again = password_again;
    }
}
