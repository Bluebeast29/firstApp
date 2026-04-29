package com.example.firstapp.api;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        String path = original.url().encodedPath();

        // Skip auth header for public endpoints
        if (path.contains("/auth/login") ||
                path.contains("/auth/register") ||
                path.contains("/auth/forgot-password") ||
                path.contains("/auth/reset-password") ||
                path.contains("/health")) {
            return chain.proceed(original);
        }

        String token = tokenManager.getAccessToken();
        if (token != null && !token.isEmpty()) {
            // Trim token to remove any accidental whitespace/newlines
            String cleanToken = token.trim();
            
            Request authenticated = original.newBuilder()
                    .header("Authorization", "Bearer " + cleanToken)
                    .header("Accept", "application/json")
                    .build();
            return chain.proceed(authenticated);
        }

        return chain.proceed(original);
    }
}
