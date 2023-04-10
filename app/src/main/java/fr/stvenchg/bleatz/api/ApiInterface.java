package fr.stvenchg.bleatz.api;

import fr.stvenchg.bleatz.api.account.AccountResponse;
import fr.stvenchg.bleatz.api.login.LoginRequest;
import fr.stvenchg.bleatz.api.login.LoginResponse;
import fr.stvenchg.bleatz.api.phone.send.PhoneSendRequest;
import fr.stvenchg.bleatz.api.phone.send.PhoneSendResponse;
import fr.stvenchg.bleatz.api.phone.verify.PhoneVerifyRequest;
import fr.stvenchg.bleatz.api.phone.verify.PhoneVerifyResponse;
import fr.stvenchg.bleatz.api.refreshToken.RefreshTokenRequest;
import fr.stvenchg.bleatz.api.refreshToken.RefreshTokenResponse;
import fr.stvenchg.bleatz.api.register.RegistrationRequest;
import fr.stvenchg.bleatz.api.register.RegistrationResponse;
import fr.stvenchg.bleatz.api.set.SetRequest;
import fr.stvenchg.bleatz.api.set.SetResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("register")
    Call<RegistrationResponse> registerUser(@Body RegistrationRequest registrationRequest);

    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("refresh-token")
    Call<RefreshTokenResponse> refreshUserToken(@Body RefreshTokenRequest refreshTokenRequest);

    @GET("account")
    Call<AccountResponse> getAccount(@Header("Authorization") String accessToken);

    @POST("phone/send")
    Call<PhoneSendResponse> sendPhoneCode(@Header("Authorization") String accessToken, @Body PhoneSendRequest phoneSendRequest);

    @POST("phone/verify")
    Call<PhoneVerifyResponse> verifyPhoneCode(@Header("Authorization") String accessToken, @Body PhoneVerifyRequest phoneVerifyRequest);

    @POST("set")
    Call<SetResponse> setAddress(@Header("Authorization") String accessToken, @Body SetRequest setRequest);

}