package no.oslomet.authenticationfrontendmicroservice.service;



import no.oslomet.authenticationfrontendmicroservice.model.ForumUser;
import no.oslomet.authenticationfrontendmicroservice.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

        //String BASE_URL = "http://157.230.115.62:9090/users";
        String BASE_URL = "http://localhost:9090/users";
        private RestTemplate restTemplate = new RestTemplate();

        public List<User> getAllUsers(){
                return  Arrays.stream(
                        restTemplate.getForObject(BASE_URL, User[].class)
                ).collect(Collectors.toList());
        }

        public User getUserById(long id){
                User User = restTemplate.getForObject(BASE_URL+"/"+id, User.class);
                return User;
        }

        public User saveUser(User newUser){
                return restTemplate.postForObject(BASE_URL, newUser, User.class);
        }

        public void updateUser(long id, User updatedUser){
                restTemplate.put(BASE_URL+"/"+id, updatedUser);
        }

        public void deleteUserById(long id){
                restTemplate.delete(BASE_URL+"/"+id);
        }


        public Optional<User> findUserByEmail(String email)
        {
              User user = restTemplate.getForObject(BASE_URL+"/findEmail/" + email, User.class);
              return Optional.ofNullable(user);
        }

}
