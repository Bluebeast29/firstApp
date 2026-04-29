package com.example.firstapp.data;

import android.content.Context;

import com.example.firstapp.api.ApiClient;
import com.example.firstapp.api.TokenManager;
import com.example.firstapp.api.dto.AuthResponse;
import com.example.firstapp.api.dto.LoginRequest;
import com.example.firstapp.api.service.AuthService;
import com.example.firstapp.data.model.LoggedInUser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDataSource {

    public interface AuthCallback {
        void onSuccess(LoggedInUser user);
        void onError(String message);
    }

    private final AuthService authService;
    private final TokenManager tokenManager;

    public LoginDataSource(Context context) {
        authService = ApiClient.getInstance(context).create(AuthService.class);
        tokenManager = TokenManager.getInstance(context);
    }

    public void login(String email, String password, AuthCallback callback) {
        LoginRequest request = new LoginRequest(email, password, true);
        authService.login(request).enqueue(new Callback<com.example.firstapp.api.ApiResponse<AuthResponse>>() {
            @Override
            public void onResponse(Call<com.example.firstapp.api.ApiResponse<AuthResponse>> call,
                                   Response<com.example.firstapp.api.ApiResponse<AuthResponse>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    AuthResponse authResponse = response.body().getData();
                    AuthResponse.UserData user = authResponse.getUser();

                    // Save tokens
                    tokenManager.saveTokens(authResponse.getAccessToken(), authResponse.getRefreshToken());
                    tokenManager.saveUser(user.getId(), user.getName(), user.getEmail(), user.getRole());

                    LoggedInUser loggedInUser = new LoggedInUser(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole(),
                            authResponse.getAccessToken(),
                            authResponse.getRefreshToken()
                    );
                    callback.onSuccess(loggedInUser);
                } else {
                    String error = "Login failed";
                    if (response.body() != null && response.body().getError() != null) {
                        error = response.body().getError();
                    } else if (response.code() == 401) {
                        error = "Invalid email or password";
                    } else if (response.code() == 403) {
                        error = "Account not approved yet";
                    }
                    callback.onError(error);
                }
            }

            @Override
            public void onFailure(Call<com.example.firstapp.api.ApiResponse<AuthResponse>> call, Throwable t) {
                String message = t.getMessage();
                if (t instanceof IOException) {
                    message = "Network error. Check your connection.";
                }
                callback.onError(message);
            }
        });
    }

    public void logout() {
        tokenManager.clear();
    }
}
