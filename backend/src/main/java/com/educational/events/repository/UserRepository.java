package com.educational.events.repository;

import com.educational.events.model.ITMOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<ITMOUser, UUID> {

    /**
     * Поиск по имени пользователя.
     *
     * @param username - имя
     * @return - пользователь из базы
     */
    Optional<ITMOUser> findByUsername(String username);
}
