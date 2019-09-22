package no.oslomet.authenticationfrontendmicroservice.service;

import no.oslomet.authenticationfrontendmicroservice.model.Channel;
import no.oslomet.authenticationfrontendmicroservice.model.ForumUser;
import no.oslomet.authenticationfrontendmicroservice.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumUserService {
        String BASE_URL = "http://localhost:9091/users";
        private RestTemplate restTemplate = new RestTemplate();


        public List<ForumUser> getAllForumUsers() {
                return Arrays.stream(
                        restTemplate.getForObject(BASE_URL, ForumUser[].class)
                ).collect(Collectors.toList());
        }

        public ForumUser getForumUserById(long id) {
                return  restTemplate.getForObject(BASE_URL + "/" + id, ForumUser.class);

        }

        public void saveForumUser(ForumUser forumUser) {
                restTemplate.postForObject(BASE_URL, forumUser, ForumUser.class);
        }
        public void addSubscription(ForumUser user, String channel)
        {
                restTemplate.postForObject(BASE_URL+"/subto/" + channel, user , ForumUser.class);
        }

        public void followUser(ForumUser user, long id)
        {
                restTemplate.postForObject(BASE_URL + "/followUser/" + id, user, ForumUser.class);
        }
        public List<ForumUser> getFollowing(long id)
        {
                return Arrays.stream(
                        restTemplate.getForObject(BASE_URL+"/getFollowing/" + id , ForumUser[].class)
                ).collect(Collectors.toList());
        }
}