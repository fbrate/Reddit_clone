package no.oslomet.authenticationfrontendmicroservice.service;


import no.oslomet.authenticationfrontendmicroservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

        @Autowired
        private UserService userService;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Optional<User> user = userService.findUserByEmail(email);
                if(!user.isPresent()) throw new UsernameNotFoundException("Not found user with email" + email);
                return getUserDetails(user.get());
        }

        public UserDetails getUserDetails(User user){
                return org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRoles())
                        .build();
        }
}