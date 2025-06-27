package com.educational.events.usecase.user;

import com.educational.events.model.AuthOperationResult;
import com.educational.events.model.BaseOperationResult;
import com.educational.events.model.enums.OperationStatus;
import com.educational.events.transfer.JwtRequestTo;
import com.educational.events.transfer.NewUserDataTo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import utils.JwtTokenUtils;

/**
 * Сервис для аутентификации и регистрации пользователей
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * Метод для создания токена аутентификации
     *
     * @param jwtRequestTo запрос с данными для аутентификации (имя пользователя и пароль)
     * @return ответ с токеном или ошибкой аутентификации
     */
    public AuthOperationResult createAuthToken(JwtRequestTo jwtRequestTo) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestTo.getUsername(),
                jwtRequestTo.getPassword()));
        } catch (BadCredentialsException e) {
            return AuthOperationResult
                .builder()
                .withStatus(OperationStatus.FAILED)
                .withMessage("Token obtain failed! Username or password is incorrect!")
                .build();
        }
        UserDetails userDetails = userService.loadUserByUsername(jwtRequestTo.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return AuthOperationResult
            .builder()
            .withStatus(OperationStatus.OK)
            .withToken(token)
            .withMessage("Token obtain success!")
            .build();
    }

    /**
     * Метод для создания нового пользователя
     *
     * @param newData данные для регистрации нового пользователя
     * @return ответ с успешным сообщением или ошибкой регистрации
     */
    public BaseOperationResult createNewUser(NewUserDataTo newData) {

        if (!newData.getPassword().equals(newData.getConfirmedPassword())) {
            return BaseOperationResult
                .builder()
                .withStatus(OperationStatus.FAILED)
                .withMessage("User creation filed! Password and confirmed password not equals!")
                .build();
        }

        if (userService.findByUsername(newData.getUsername()).isPresent()) {
            return BaseOperationResult
                .builder()
                .withStatus(OperationStatus.FAILED)
                .withMessage("User creation filed! User with this username already exist.")
                .build();
        }

        var createdUser = userService.createUser(newData);
        return BaseOperationResult
            .builder()
            .withStatus(OperationStatus.OK)
            .withEntityId(createdUser.getId())
            .withMessage("User creation success")
            .build();
    }
}
