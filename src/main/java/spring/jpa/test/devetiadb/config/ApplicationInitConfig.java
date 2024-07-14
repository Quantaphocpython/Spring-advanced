package spring.jpa.test.devetiadb.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.jpa.test.devetiadb.entity.User;
import spring.jpa.test.devetiadb.enums.Role;
import spring.jpa.test.devetiadb.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    //ApplicationRunner sẽ được khởi chạy mỗi lần ta start
    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
           if(userRepository.findByName("admin").isEmpty()) {
               Set<String> roles = new HashSet<>();
               roles.add(Role.ADMIN.name());
               User admin = User.builder()
                       .name("admin")
                       .password(passwordEncoder.encode("admin"))
//                       .roles(roles)
                       .build();
               userRepository.save(admin);
               log.info("Admin user has been created with default password: admin, please change it");
           }
        };
    }

}
