package fr.stvenchg.bleatz.api.phone.send;

public class PhoneSendRequest {
    private String phone_number;

    public PhoneSendRequest(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhoneNumber() {
        return this.phone_number;
    }
}