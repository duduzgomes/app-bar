package com.eduardogomes.gomes_bar.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eduardogomes.gomes_bar.entities.User;

public interface UserRepository extends JpaRepository<User,UUID>{

    public Optional<User> findByUsername(String username);
}
