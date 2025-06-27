package com.educational.events.usecase.user;

import com.educational.events.model.BaseOperationResult;
import com.educational.events.model.ITMOUser;
import com.educational.events.repository.UserRepository;
import com.educational.events.transfer.NewUserDataTo;
import com.educational.events.usecase.role.RoleFetchUseCase;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователями и управления их данными.
 * Реализует интерфейс UserDetailsService для интеграции с Spring Security.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Getter
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleFetchUseCase roleFetchUseCase;

    /**
     * Ищет пользователя по его id.
     *
     * @param id - id
     * @return объект Optional с пользователем, если он найден.
     */
    public Optional<ITMOUser> findById(UUID id) {
        return userRepository.findById(id);
    }


    /**
     * Ищет пользователя по его имени пользователя.
     *
     * @param username имя пользователя.
     * @return объект Optional с пользователем, если он найден.
     */
    public Optional<ITMOUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Загружает пользователя по его имени пользователя.
     * Реализация метода из интерфейса UserDetailsService.
     *
     * @param username имя пользователя.
     * @return объект UserDetails с информацией о пользователе.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User : \"" + username + "\" not found!"));

        // Пользователь spring
        return new User(user.getUsername(), user.getPassword(),
            user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList())
        );
    }

    /**
     * Создает нового пользователя.
     *
     * @param newDAta объект DTO с данными для регистрации пользователя.
     * @return созданный пользователь.
     */
    public ITMOUser createUser(NewUserDataTo newDAta) {
        ITMOUser user = new ITMOUser();
        user.setId(UUID.randomUUID());
        user.setEmail(newDAta.getEmail());
        user.setUsername(newDAta.getUsername());
        user.setPassword(passwordEncoder.encode(newDAta.getPassword()));
        user.setRoles(List.of(roleFetchUseCase.getUserRole()));
        return userRepository.save(user);
    }

    public ITMOUser updateUser(ITMOUser updated) {
        return userRepository.save(updated);
    }
}
