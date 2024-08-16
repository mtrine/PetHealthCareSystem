package com.group07.PetHealthCare.config;

import com.group07.PetHealthCare.enumData.Role;
import com.group07.PetHealthCare.pojo.Admin;
import com.group07.PetHealthCare.pojo.User;
import com.group07.PetHealthCare.respositytory.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(IUserRepository userRepository) {
        return args -> {
            if(userRepository.findByRole(Role.ADMIN)==null) {
                User user = new Admin();
                user.setRole(Role.ADMIN);
                user.setEmail("admin@group07.com");
                user.setPassword("admin");
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin");
            }
        };
    };
}
