package com.educational.events.controller;

import com.educational.events.model.AuthOperationResult;
import com.educational.events.model.BaseOperationResult;
import com.educational.events.transfer.JwtRequestTo;
import com.educational.events.transfer.NewUserDataTo;
import com.educational.events.transfer.UpdateUserData;
import com.educational.events.usecase.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
@EnableAspectJAutoProxy
public class UserController {

    private final AuthService authService;

    @PostMapping("/authorization")
    public AuthOperationResult createAuthToken(@RequestBody JwtRequestTo authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PutMapping("/registration")
    public BaseOperationResult createNewUser(@RequestBody NewUserDataTo newUserDataTo) {
        return authService.createNewUser(newUserDataTo);
    }

    @PutMapping("/update")
    public BaseOperationResult updateUser(@RequestBody UpdateUserData newUserDataTo) {
        return authService.updateUser(newUserDataTo);
    }
}
