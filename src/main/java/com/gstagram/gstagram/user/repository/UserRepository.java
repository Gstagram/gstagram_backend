package com.gstagram.gstagram.user.repository;

import com.gstagram.gstagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUuid(String uuid);
    Optional<User> findByEmail(String email);
    List<User> getUsersByUuidIn(List<String> uuidList);
    boolean existsByUuid(String uuid);

}
