package br.ufrn.plataformaproad.util;

import br.ufrn.plataformaproad.domain.User;
import br.ufrn.plataformaproad.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DbSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("programmerdavi@gmail.com").isEmpty()) {
            User admin = User.builder()
                    .username("programmerdavi@gmail.com")
                    .password(passwordEncoder.encode("12345678"))
                    .role("ADMIN")
                    .build();

            userRepository.save(admin);
            System.out.println("✅ Usuário admin criado: programmerdavi@gmail / 12345678");
        } else {
            System.out.println("ℹ️ Usuário admin já existe, não foi criado novamente.");
        }
    }
}
