package fr.stvenchg.bleatz.api;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthenticationManager {
    private static final String SHARED_PREFS_NAME = "authentication_manager";
    private static final String ACCESS_TOKEN_KEY = "token";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";

    private static final String USER_ID = "idUser";

    private static final String FIRSTNAME = "firstname";

    private static final String LASTNAME = "lastname";
    private static final String EMAIL = "email";

    private static final String PHONE = "phone";

    private static final String CREATION_DATE = "creation_date";

    private static final String ADDRESS = "address";

    private SharedPreferences sharedPreferences;

    public AuthenticationManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveTokens(String email, String accessToken, String refreshToken) {
        sharedPreferences.edit()
                .putString(EMAIL, email)
                .putString(ACCESS_TOKEN_KEY, accessToken)
                .putString(REFRESH_TOKEN_KEY, refreshToken)
                .apply();
    }

    public void saveUserDetails(String idUser, String firstname, String lastname, String email, String phone, String creation_date, String address) {
        sharedPreferences.edit()
                .putString(USER_ID, idUser)
                .putString(FIRSTNAME, firstname)
                .putString(LASTNAME, lastname)
                .putString(EMAIL, email)
                .putString(PHONE, phone)
                .putString(CREATION_DATE, creation_date)
                .putString(ADDRESS, address)
                .apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null);
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, null);
    }

    public String getFirstname() {
        return sharedPreferences.getString(FIRSTNAME, null);
    }
    public String getLastname() {
        return sharedPreferences.getString(LASTNAME, null);
    }

    public String getPhone() {
        return sharedPreferences.getString(PHONE, null);
    }

    public String getCreationDate() {
        return sharedPreferences.getString(CREATION_DATE, null);
    }

    public String getAddress() {
        return sharedPreferences.getString(ADDRESS, null);
    }

    public void setAddress(String address) {
        sharedPreferences.edit()
                .putString(ADDRESS, address)
                .apply();
    }

    public void clearTokens() {
        sharedPreferences.edit()
                .remove(ACCESS_TOKEN_KEY)
                .remove(REFRESH_TOKEN_KEY)
                .remove(EMAIL)
                .remove(USER_ID)
                .remove(FIRSTNAME)
                .remove(LASTNAME)
                .remove(PHONE)
                .remove(CREATION_DATE)
                .remove(ADDRESS)
                .apply();
    }
}