package com.backend.app.userservice.services.interfaces;

import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.userservice.models.SignInRequest;
import com.backend.app.userservice.models.SignUpRequest;

public interface UserServiceInterface {
    public BaseResponse<String> signIn(SignInRequest request);
    public BaseResponse<String> createUserFromSignUp(SignUpRequest request);
} 
    
