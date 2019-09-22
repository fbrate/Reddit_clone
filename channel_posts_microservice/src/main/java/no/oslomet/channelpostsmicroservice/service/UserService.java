package no.oslomet.channelpostsmicroservice.service;



import no.oslomet.channelpostsmicroservice.model.Post;
import no.oslomet.channelpostsmicroservice.model.User;
import no.oslomet.channelpostsmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
        @Autowired
        private UserRepository userRepository;
        @Autowired
                private PostService postService;

        String BASE_URL = "http://localhost:9090/users";
        private RestTemplate restTemplate = new RestTemplate();

        // updates entire database from server.
        public void updateAllUserDataFromServer(){
                List<User> users = Arrays.stream(
                        restTemplate.getForObject(BASE_URL, User[].class)
                ).collect(Collectors.toList());
                for(User u : users)
                {

                        saveUser(u);
                }
        }
        // Updates one user through id from database. Usually called after a save/update.


        public List<User> getAllUsers() {
                return userRepository.findAll();
        }


        public User getUserById(long id) {
                return userRepository.findById(id).get();
        }

        public void saveUser(User user) {
                if(user.getPosts() != null) {
                        List<Post> posts = new ArrayList<>();
                        // updates the name value in posts

                        for (Post p : user.getPosts()) {
                                p.setUserName(user.getFirstName() + " " + user.getLastName());
                                postService.savePost(p);
                                posts.add(p);
                        }

                        user.setPosts(posts);
                }
                userRepository.save(user);

        }

        // called when
        public void deleteUserById(long id) {
                userRepository.deleteById(id);
        }
        public void deleteUser(User user)
        {
                userRepository.delete(user);
        }


}