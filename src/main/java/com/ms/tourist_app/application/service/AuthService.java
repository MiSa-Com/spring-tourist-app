package com.ms.tourist_app.application.service;

import com.ms.tourist_app.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.output.auth.AuthenticationResponse;

import java.io.InvalidObjectException;

public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest signIn);
    AuthenticationResponse signupUser(UserDataInput userDataInput);
//    AuthenticationResponse signupUser2(UserDataInput userDataInput);
    AuthenticationResponse signupAdmin(UserDataInput userDataInput);
    AuthenticationResponse validateToken(AuthenticationResponse authenticationResponse) throws InvalidObjectException;
}