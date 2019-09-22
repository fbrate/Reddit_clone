package no.oslomet.channelpostsmicroservice;

import no.oslomet.channelpostsmicroservice.repository.ChannelRepository;
import no.oslomet.channelpostsmicroservice.repository.PostRepository;
import no.oslomet.channelpostsmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerRestProjectApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(ServerRestProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
