package no.oslomet.authenticationfrontendmicroservice.model;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {

        private long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String roles;

        public User(String firstName, String lastName, String email, String password) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.password = password;
        }


}