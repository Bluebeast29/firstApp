package com.example.firstapp.api.service;

import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.AuthResponse;
import com.example.firstapp.api.dto.LoginRequest;
import com.example.firstapp.api.dto.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/login")
    Call<ApiResponse<AuthResponse>> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<ApiResponse<AuthResponse.UserData>> register(@Body RegisterRequest request);

    @POST("auth/refresh")
    Call<ApiResponse<AuthResponse>> refreshToken(@Body RefreshRequest request);

    @POST("auth/forgot-password")
    Call<ApiResponse<Void>> forgotPassword(@Body ForgotPasswordRequest request);

    @POST("auth/logout")
    Call<ApiResponse<Void>> logout();

    @GET("auth/me")
    Call<ApiResponse<AuthResponse.UserData>> getMe();

    // Inner classes for simple request bodies
    class RefreshRequest {
        @SuppressWarnings("unused")
        public String refreshToken;
        public RefreshRequest(String refreshToken) { this.refreshToken = refreshToken; }
    }

    class ForgotPasswordRequest {
        @SuppressWarnings("unused")
        public String email;
        public ForgotPasswordRequest(String email) { this.email = email; }
    }
}
