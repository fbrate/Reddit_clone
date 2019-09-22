package no.oslomet.authenticationfrontendmicroservice.model;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class Post {
        private long id;
        private String postValue;
        private ForumUser user;
        private Channel channel;
        private String channelName;
        private String userName;
        private String title;
        private long userId;
        private long likes;

        public void incrementLike(){
                likes++;
        }
}
