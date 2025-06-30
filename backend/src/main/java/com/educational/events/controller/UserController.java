package com.educational.events.controller;

import com.educational.events.model.AuthOperationResult;
import com.educational.events.model.BaseOperationResult;
import com.educational.events.transfer.JwtRequestTo;
import com.educational.events.transfer.NewUserDataTo;
import com.educational.events.transfer.TokenPair;
import com.educational.events.transfer.UpdateUserData;
import com.educational.events.usecase.user.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(
    origins = "http://localhost:4200",
    allowCredentials = "true"
)
@RequestMapping("/user")
@EnableAspectJAutoProxy
public class UserController {

    private final AuthService auth;
    private final AuthenticationManager authManager;

    @PostMapping("/authorization")
    public ResponseEntity<TokenPair> login(@RequestBody JwtRequestTo dto,
                                           HttpServletResponse resp) {
        return auth.login(dto, resp, authManager);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenPair> refresh(
            @CookieValue(value = "refresh", required = false) String refresh,
            HttpServletResponse resp) {

        if (refresh == null || refresh.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return auth.refresh(refresh, resp);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse resp) {
        auth.logout(resp);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/registration")
    public BaseOperationResult createNewUser(@RequestBody NewUserDataTo newUserDataTo) {
        return auth.createNewUser(newUserDataTo);
    }

    @PutMapping("/update")
    public BaseOperationResult updateUser(@RequestBody UpdateUserData newUserDataTo) {
        return auth.updateUser(newUserDataTo);
    }

}
