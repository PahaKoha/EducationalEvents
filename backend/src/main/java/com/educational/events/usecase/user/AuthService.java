package com.educational.events.usecase.user;

import com.educational.events.model.AuthOperationResult;
import com.educational.events.model.BaseOperationResult;
import com.educational.events.model.EventTypeEntity;
import com.educational.events.model.ITMOUser;
import com.educational.events.model.SphereEntity;
import com.educational.events.model.enums.OperationStatus;
import com.educational.events.transfer.JwtRequestTo;
import com.educational.events.transfer.NewUserDataTo;
import com.educational.events.transfer.UpdateUserData;
import com.educational.events.utils.JwtTokenUtils;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
    public BaseOperationResult updateUser(UpdateUserData newData) {

        if (!newData.getUpdatedPassword().equals(newData.getConfirmedUpdatedPassword())) {
            return BaseOperationResult
                .builder()
                .withStatus(OperationStatus.FAILED)
                .withMessage("User update filed! Password and confirmed password not equals!")
                .build();
        }

        var existed = userService.findById(newData.getId());

        if (existed.isEmpty()) {
            return BaseOperationResult
                .builder()
                .withStatus(OperationStatus.FAILED)
                .withMessage("User creation filed! User with this id is not exist.")
                .build();
        }

        setExistedUserData(existed.get(), newData);

        var createdUser = userService.updateUser(existed.get());

        return BaseOperationResult
            .builder()
            .withStatus(OperationStatus.OK)
            .withEntityId(createdUser.getId())
            .withMessage("User creation success")
            .build();
    }

    private void setExistedUserData (ITMOUser existedUserData, UpdateUserData updateUserData) {
        if (updateUserData.getUpdatedPassword() != null
            && !updateUserData.getUpdatedPassword().isEmpty()
            && !updateUserData.getUpdatedPassword().isBlank()) {
            existedUserData.setPassword(userService.getPasswordEncoder().encode(updateUserData.getUpdatedPassword()));
        }
        if (!Collections.isEmpty(updateUserData.getInterestEventTypeIds())) {
            existedUserData.setFavoritesEventTypes(updateUserData
                .getInterestEventTypeIds()
                .stream()
                .map(EventTypeEntity::new)
                .collect(Collectors.toList()));
        }
        if (!Collections.isEmpty(updateUserData.getInterestSphereIds())) {
            existedUserData.setFavoritesSpheres(updateUserData
                .getInterestSphereIds()
                .stream()
                .map(SphereEntity::new)
                .collect(Collectors.toList()));
        }
    }

    /**
     * Метод для обновления нового пользователя
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
