package fr.stvenchg.bleatz.tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.stvenchg.bleatz.AuthActivity;
import fr.stvenchg.bleatz.MainActivity;
import fr.stvenchg.bleatz.RegisterActivity;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.refreshToken.RefreshTokenRequest;
import fr.stvenchg.bleatz.api.refreshToken.RefreshTokenResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefreshTokenTask extends AsyncTask<Void, Void, Boolean> {

    private AuthActivity authActivity;
    private final String email;
    private final String refreshToken;
    private final AuthenticationManager authenticationManager;

    public RefreshTokenTask(AuthActivity authActivity, String email, String refreshToken, AuthenticationManager authenticationManager) {
        this.authActivity = authActivity;
        this.email = email;
        this.refreshToken = refreshToken;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicBoolean success = new AtomicBoolean(false);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RefreshTokenResponse> call = apiInterface.refreshUserToken(new RefreshTokenRequest(email, refreshToken));

        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.isSuccessful()) {
                    RefreshTokenResponse refreshTokenResponse = response.body();
                    if (refreshTokenResponse != null && refreshTokenResponse.isSuccess()) {
                        authenticationManager.saveTokens(authenticationManager.getEmail(), refreshTokenResponse.getToken(), authenticationManager.getRefreshToken());

                        System.out.println(authenticationManager.getEmail());
                        System.out.println(authenticationManager.getAccessToken());
                        System.out.println(authenticationManager.getRefreshToken());

                        success.set(true);
                    }
                } else {
                    authenticationManager.clearTokens();
                    success.set(false);
                }
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                Log.e("AuthActivity", "refreshAccessToken onFailure: " + t.getMessage());
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return success.get();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (result) {
            Intent mainActivityIntent = new Intent(authActivity, MainActivity.class);
            authActivity.startActivity(mainActivityIntent);
            authActivity.finish();
        }
        else {
            Toast.makeText(authActivity, "La session a expirée.", Toast.LENGTH_SHORT).show();
        }
    }
}
