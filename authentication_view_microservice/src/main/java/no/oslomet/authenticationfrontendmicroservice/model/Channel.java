package no.oslomet.authenticationfrontendmicroservice.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class Channel {
        private String title;
        private List<Post> posts;
        private List<Long> postIds;
        private List<ForumUser> users;
}
