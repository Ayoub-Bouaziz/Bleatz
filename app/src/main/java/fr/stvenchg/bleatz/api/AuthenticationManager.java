package fr.stvenchg.bleatz.api;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthenticationManager {
    private static final String SHARED_PREFS_NAME = "authentication_manager";
    private static final String ACCESS_TOKEN_KEY = "token";
    private static final String REFRESH_TOKEN_KEY = "refresh_token";

    private SharedPreferences sharedPreferences;

    public AuthenticationManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveTokens(String accessToken, String refreshToken) {
        sharedPreferences.edit()
                .putString(ACCESS_TOKEN_KEY, accessToken)
                .putString(REFRESH_TOKEN_KEY, refreshToken)
                .apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null);
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null);
    }

    public void clearTokens() {
        sharedPreferences.edit()
                .remove(ACCESS_TOKEN_KEY)
                .remove(REFRESH_TOKEN_KEY)
                .apply();
    }
}