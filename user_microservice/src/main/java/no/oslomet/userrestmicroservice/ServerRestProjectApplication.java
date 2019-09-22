package no.oslomet.userrestmicroservice;

import no.oslomet.userrestmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerRestProjectApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ServerRestProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
