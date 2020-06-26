package net.lz1998.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringCqDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCqDemoApplication.class, args);
    }

    // TODO 暂时先放在这
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
