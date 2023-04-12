package fr.stvenchg.bleatz.api;

import java.util.List;

import fr.stvenchg.bleatz.api.account.AccountResponse;
import fr.stvenchg.bleatz.api.boisson.BoissonResponse;
import fr.stvenchg.bleatz.api.burger.BurgerResponse;
import fr.stvenchg.bleatz.api.burger.DetailsBurgerResponse;

import fr.stvenchg.bleatz.api.login.LoginRequest;
import fr.stvenchg.bleatz.api.login.LoginResponse;

import fr.stvenchg.bleatz.api.panier.AddToCartRequest;
import fr.stvenchg.bleatz.api.panier.AddToCartResponse;
import fr.stvenchg.bleatz.api.panier.CartResponse;
import fr.stvenchg.bleatz.api.panier.CreateMenuResponse;
import fr.stvenchg.bleatz.api.panier.DeleteFromCartResponse;
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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    @GET("products/burger")
    Call<BurgerResponse> getBurgers();
    @GET("products/boisson")
    Call<BoissonResponse> getBoissons();
    @GET("products/burger")
    Call<DetailsBurgerResponse> getBurgersDetails(@Query("id") int idBurger);
    @GET("cart")
    Call<CartResponse> getCart(@Header("Authorization") String token);
    @DELETE("cart")
    Call<List<CartResponse>> clearCart(@Header("Authorization") String accessToken);
    @POST("cart")
    Call<AddToCartResponse> addToCart(@Header("Authorization") String accessToken, @Body AddToCartRequest addToCartRequest);
    @POST("menu/create")
    Call<CreateMenuResponse> createMenu(@Header("Authorization") String accessToken, @Query("idBurger") int burgerId, @Query("idBoisson") int boissonId);
    @DELETE("cart")
    Call<DeleteFromCartResponse> deleteFromCart(@Header("Authorization") String accessToken, @Query("idMenu") int menuId);
    @POST("set")
    Call<SetResponse> setAddress(@Header("Authorization") String accessToken, @Body SetRequest setRequest);

}
