package no.oslomet.authenticationfrontendmicroservice.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ForumUser {
        private long id;
        private String firstName;
        private String lastName;
        private List<Channel> channels;
        // channel subs
        // followers
}
