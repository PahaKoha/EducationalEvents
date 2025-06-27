package com.educational.events.usecase.role;


import com.educational.events.model.RoleEntity;
import com.educational.events.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для обработки различных действий с ролями
 */
@Component
@RequiredArgsConstructor
public class RoleFetchUseCase {

    private final RoleRepository roleRepository;

    /**
     * Метод находит роль ROLE_USER в базы данных
     * @return - Role - ROLE_USER
     */
    @Transactional
    public RoleEntity getUserRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow(RuntimeException::new);
    }
    /**
     * Метод находит роль ROLE_USER в базы данных
     * @return - Role - ROLE_ADMIN
     */
    @Transactional
    public RoleEntity getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").orElseThrow(RuntimeException::new);
    }

    /**
     * Метод находит роль ROLE_USER в базы данных
     * @return - Role - ROLE_SUPER_ADMIN
     */
    @Transactional
    public RoleEntity getSuperAdminRole() {
        return roleRepository.findByName("ROLE_SUPER_ADMIN").orElseThrow(RuntimeException::new);
    }
}
