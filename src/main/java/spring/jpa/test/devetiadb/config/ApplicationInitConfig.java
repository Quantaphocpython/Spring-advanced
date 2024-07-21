package spring.jpa.test.devetiadb.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import spring.jpa.test.devetiadb.entity.User;
import spring.jpa.test.devetiadb.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    // ApplicationRunner sẽ được khởi chạy mỗi lần ta start
    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver") // bean này sẽ chỉ được init khi spring đọc file property là
    // test.properties hay cụ thể hơn khi driver là mysql
    public ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByName("admin").isEmpty()) {
                User admin = User.builder()
                        .name("admin")
                        .password(passwordEncoder.encode("admin"))
                        .build();
                userRepository.save(admin);
                log.info("Admin user has been created with default password: admin, please change it");
            }
        };
    }
}
