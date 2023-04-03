package fr.stvenchg.bleatz.api.phone.verify;

public class PhoneVerifyRequest {
    private String phone_number;
    private String code;

    public PhoneVerifyRequest(String phone_number, String code) {
        this.phone_number = phone_number;
        this.code = code;
    }
}