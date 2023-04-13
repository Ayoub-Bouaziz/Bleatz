package fr.stvenchg.bleatz.api;

import java.util.List;

import fr.stvenchg.bleatz.api.account.AccountResponse;
import fr.stvenchg.bleatz.api.boisson.BoissonResponse;
import fr.stvenchg.bleatz.api.burger.BurgerResponse;
import fr.stvenchg.bleatz.api.burger.CreateBurgerResponse;
import fr.stvenchg.bleatz.api.burger.DetailsBurgerResponse;

import fr.stvenchg.bleatz.api.complete.CompleteResponse;
import fr.stvenchg.bleatz.api.composer.AddComposerResponse;
import fr.stvenchg.bleatz.api.ingredient.IngredientResponse;
import fr.stvenchg.bleatz.api.kitchen.KitchenResponse;
import fr.stvenchg.bleatz.api.kitchen.OrderContentResponse;
import fr.stvenchg.bleatz.api.kitchen.ValidateOrderResponse;
import fr.stvenchg.bleatz.api.login.LoginRequest;
import fr.stvenchg.bleatz.api.login.LoginResponse;

import fr.stvenchg.bleatz.api.orders.OrdersResponse;
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

    @GET("kitchen/orders")
    Call<KitchenResponse> getKitchenOrders(@Header("Authorization") String accessToken);

    @GET("kitchen/order-content")
    Call<OrderContentResponse> getOrderContent(@Header("Authorization") String accessToken, @Query("id") int idOrder);

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
    @GET("cart")
    Call<AddToCartResponse> addToCart(@Header("Authorization") String accessToken, @Query("add") int idMenu);
    @GET("menu/create")
    Call<CreateMenuResponse> createMenu(@Header("Authorization") String accessToken, @Query("idBurger") int burgerId, @Query("idBoisson") int boissonId);
    @DELETE("cart")
    Call<DeleteFromCartResponse> deleteFromCart(@Header("Authorization") String accessToken, @Query("idMenu") int menuId);
    @POST("set")
    Call<SetResponse> setAddress(@Header("Authorization") String accessToken, @Body SetRequest setRequest);
    @GET("complete")
    Call<CompleteResponse> getComplete(@Header("Authorization") String token);
    @GET("kitchen/validate-order")
    Call<ValidateOrderResponse> validateOrder(@Header("Authorization") String accessToken, @Query("id") int idOrder);
    @GET("orders")
    Call<OrdersResponse> getOrders(@Header("Authorization") String accessToken);

    @GET("admin/ingredients/list")
    Call<IngredientResponse> getIngredients(@Header("Authorization") String accessToken);
    @GET("admin/create-burger")
    Call<CreateBurgerResponse> createBurger(@Header("Authorization") String accessToken, @Query("nom") String nom, @Query("prix") double prix, @Query("description") String description );

    @GET("admin/ingredients/add-composer")
    Call<AddComposerResponse> addComposer(@Header("Authorization") String accessToken, @Query("idBurger") int idBurger, @Query("idIngredient") int idIngredient );

}
