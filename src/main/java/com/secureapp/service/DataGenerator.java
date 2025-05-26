package com.secureapp.service;

import com.secureapp.enums.Role;
import com.secureapp.model.UserProfile;
import com.secureapp.respository.UserRepository;
import lombok.Getter;
import net.datafaker.Faker;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.CharacterData;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class DataGenerator {

    @Getter
    @Value("${GENERAL_PASSWORD}")
    private String password;

    @Bean
    @Order(value = 1)
    public CommandLineRunner insertUserProfiles(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ){
        return args -> {
            if(userRepository.count() > 0){
                return;
            }

            UserProfile admin = UserProfile.builder()
                    .id(null)
                    .firstName("Admin")
                    .lastName("Admin")
                    .email("zingadeprathamesh12@gmail.com")
                    .password(passwordEncoder.encode(this.getPassword()))
                    .enabled(true)
                    .accountLocked(false)
                    .role(Role.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(admin);

            Faker faker = new Faker();
            Set<UserProfile> userProfiles = new HashSet<>();
            for(int i = 0; i < 100; i++){
                String firstname = faker.name().firstName();
                String lastname = faker.name().lastName();
                String password = generateRandomSpecialCharacters(10);

                userProfiles.add(UserProfile.builder()
                                .createdAt(LocalDateTime.now())
                                .accountLocked(false)
                                .enabled(true)
                                .email(firstname + "."+ lastname + i + "@gmail.com")
                                .password(passwordEncoder.encode(password))
                                .firstName(firstname)
                                .lastName(lastname)
                                .id(null)
                                .role(Role.STUDENT)
                        .build());
            }
            userRepository.saveAll(userProfiles);
        };
    }

    public String generateRandomSpecialCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45)
                .build();
        return pwdGenerator.generate(length);
    }
}
