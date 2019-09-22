package no.oslomet.userrestmicroservice.service;



import no.oslomet.userrestmicroservice.model.User;
import no.oslomet.userrestmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class UserService {
        @Autowired
        private UserRepository userRepository;

        String BASE_URL = "http://localhost:9091/users";
        private RestTemplate restTemplate = new RestTemplate();

        public List<User> getAllUsers(){
                return userRepository.findAll();
        }


        public User getUserById(long id){
                return userRepository.findById(id).get();
        }

        public void saveUser (User user ){
                userRepository.save(user);
                restTemplate.postForObject(BASE_URL, user, User.class);
        }
        public void updateUser(long id, User user){
                userRepository.save(user);
                restTemplate.put(BASE_URL+"/"+id, user);

        }

        public void deleteUserById(long id ){
                restTemplate.delete(BASE_URL+"/"+id);
                userRepository.deleteById(id);
        }


        public User findUserByEmail(String email)
        {
               return userRepository.findUserByEmail(email).get();
        }

}