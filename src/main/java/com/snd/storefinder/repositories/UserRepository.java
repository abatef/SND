package com.snd.storefinder.repositories;

import com.snd.storefinder.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Integer id);
    boolean existsUserByEmail(String email);
}
