package com.example.firstapp.data;

import android.content.Context;

import com.example.firstapp.api.TokenManager;
import com.example.firstapp.data.model.LoggedInUser;

public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;
    private TokenManager tokenManager;
    private LoggedInUser user = null;

    private LoginRepository(LoginDataSource dataSource, TokenManager tokenManager) {
        this.dataSource = dataSource;
        this.tokenManager = tokenManager;
    }

    public static LoginRepository getInstance(Context context) {
        if (instance == null) {
            LoginDataSource dataSource = new LoginDataSource(context);
            TokenManager tokenManager = TokenManager.getInstance(context);
            instance = new LoginRepository(dataSource, tokenManager);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        if (user != null) return true;
        // Check persisted tokens
        if (tokenManager.isLoggedIn()) {
            user = new LoggedInUser(
                    tokenManager.getUserId(),
                    tokenManager.getUserName(),
                    tokenManager.getUserEmail(),
                    tokenManager.getUserRole(),
                    tokenManager.getAccessToken(),
                    tokenManager.getRefreshToken()
            );
            return true;
        }
        return false;
    }

    public LoggedInUser getUser() {
        if (user == null && tokenManager.isLoggedIn()) {
            user = new LoggedInUser(
                    tokenManager.getUserId(),
                    tokenManager.getUserName(),
                    tokenManager.getUserEmail(),
                    tokenManager.getUserRole(),
                    tokenManager.getAccessToken(),
                    tokenManager.getRefreshToken()
            );
        }
        return user;
    }

    public void login(String email, String password, LoginDataSource.AuthCallback callback) {
        dataSource.login(email, password, new LoginDataSource.AuthCallback() {
            @Override
            public void onSuccess(LoggedInUser loggedInUser) {
                user = loggedInUser;
                callback.onSuccess(loggedInUser);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }
}
