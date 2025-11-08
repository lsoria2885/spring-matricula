package com.mitocode.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.mitocode.model.Role;
import com.mitocode.model.User;
import com.mitocode.repo.IRoleRepo;
import com.mitocode.repo.IUserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IRoleRepo roleRepo;
    private final IUserRepo userRepo;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void run(String... args) {
        Role roleUser = new Role(null, "ROLE_USER");
        Role roleAdmin = new Role(null, "ROLE_ADMIN");

        roleRepo.deleteAll()
                .thenMany(roleRepo.saveAll(List.of(roleUser, roleAdmin)))
                .then(userRepo.deleteByUsername("admin"))
                .then(userRepo.deleteByUsername("user"))
                .then(userRepo.save(new User(null, "admin", encoder.encode("admin123"), true, List.of(roleAdmin, roleUser))))
                .then(userRepo.save(new User(null, "user", encoder.encode("user123"), true, List.of(roleUser))))
                .then()
                .block();
    }
}
