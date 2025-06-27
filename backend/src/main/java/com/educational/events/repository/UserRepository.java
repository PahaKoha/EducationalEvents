package com.educational.events.repository;

import com.educational.events.model.EventTypeEntity;
import com.educational.events.model.ITMOUser;
import com.educational.events.model.SphereEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("SELECT u FROM ITMOUser u " +
        "JOIN u.favoritesSpheres s " +
        "JOIN u.favoritesEventTypes e " +
        "WHERE s IN :spheres OR e IN :eventTypes")
    List<ITMOUser> findBySpheresInAndEventTypesIn(@Param("spheres") List<SphereEntity> sphereIds,
                                                  @Param("eventTypes") List<EventTypeEntity> eventTypeIds);
}
