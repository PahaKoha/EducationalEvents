package com.educational.events.usecase.user;

import com.educational.events.config.RabbitConfig;
import com.educational.events.model.*;
import com.educational.events.model.enums.MessageType;
import com.educational.events.model.enums.OperationStatus;
import com.educational.events.transfer.JwtRequestTo;
import com.educational.events.transfer.NewUserDataTo;
import com.educational.events.transfer.TokenPair;
import com.educational.events.transfer.UpdateUserData;
import com.educational.events.utils.JwtTokenUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.stream.Collectors;

/**
 * Сервис для аутентификации и регистрации пользователей
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


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

    private void setExistedUserData(ITMOUser existedUserData, UpdateUserData updateUserData) {
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

        var mailCreateUser = new MailCreateUserTo(MessageType.REGISTRATION_USER,
                createdUser.getUsername(),
                createdUser.getEmail());

        try {
            rabbitTemplate.convertAndSend(RabbitConfig.QUEUE, objectMapper.writeValueAsString(mailCreateUser));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return BaseOperationResult
                .builder()
                .withStatus(OperationStatus.OK)
                .withEntityId(createdUser.getId())
                .withMessage("User creation success")
                .build();
    }

    public ResponseEntity<TokenPair> login(JwtRequestTo jwtRequestTo,
                                           HttpServletResponse httpServletResponse,
                                           AuthenticationManager authenticationManager) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestTo.getUsername(),
                jwtRequestTo.getPassword()));

        UserDetails ud  = userService.loadUserByUsername(jwtRequestTo.getUsername());
        String access   = jwtTokenUtils.generateAccess(ud);
        String refresh  = jwtTokenUtils.generateRefresh(ud);

        addRefreshCookie(httpServletResponse, refresh);
        return ResponseEntity.ok(new TokenPair(access, ud.getUsername()));
    }

    public ResponseEntity<TokenPair> refresh(String refreshCookie,
                                             HttpServletResponse httpServletResponse) {

        if (!jwtTokenUtils.isRefresh(refreshCookie))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String username = jwtTokenUtils.getUsername(refreshCookie);
        UserDetails ud  = userService.loadUserByUsername(username);

        String accessNew  = jwtTokenUtils.generateAccess(ud);
        String refreshNew = jwtTokenUtils.generateRefresh(ud);

        addRefreshCookie(httpServletResponse, refreshNew);
        return ResponseEntity.ok(new TokenPair(accessNew, username));
    }

    public void logout(HttpServletResponse resp) {
        ResponseCookie empty = ResponseCookie.from("refresh", "")
                .httpOnly(true).secure(true).sameSite("Strict")
                .path("/user/refresh").maxAge(0).build();
        resp.addHeader(HttpHeaders.SET_COOKIE, empty.toString());
    }

    private void addRefreshCookie(HttpServletResponse resp, String value) {
        ResponseCookie cookie = ResponseCookie.from("refresh", value)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/user/refresh")
                .maxAge(Duration.ofDays(14))
                .build();

        resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
